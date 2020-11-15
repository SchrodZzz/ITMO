'use strict';

const fetch = require('node-fetch');

const API_KEY = require('./key.json');

/**
 * @typedef {object} TripItem Город, который является частью маршрута.
 * @property {number} geoid Идентификатор города
 * @property {number} day Порядковое число дня маршрута
 */

class TripBuilder {

  constructor(geoids) {
    this.paln = []
    this.daysSup = 7
    this.geoids = geoids
  }

  __planeForecast(visitedCities, cityWithForecasts, cityId, plan) {
    const idx = visitedCities.length
    const a = this.paln[idx]
    const b = cityWithForecasts.find(cityOnPath => cityOnPath.geoid === cityId).forecasts[idx]
    if ((a === 'sunny') ? b === 'clear' || b === 'partly-cloudy' : (a === 'cloudy')
      ? b === 'cloudy' || b === 'overcast' : false) {
      return plan(visitedCities.concat([{geoid: cityId, day: visitedCities.length + 1}]))
    } else {
      return null
    }
  }

  __fill(cnt, type) {
    this.paln.push(...Array(cnt).fill(type))
  }

  __getWeather(geoid) {
    return fetch(`https://api.weather.yandex.ru/v2/forecast?hours=false&limit=7&geoid=${geoid}`, {
      headers: {
        "X-Yandex-API-Key": API_KEY.key
      }
    }).then(response => response.json())
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества солнечных дней
   * Согласно API Яндекс.Погоды, к солнечным дням
   * можно приравнять следующие значения `condition`:
   * * `clear`;
   * * `partly-cloudy`.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  sunny(daysCount) {
    this.__fill(daysCount, 'sunny')
    return this
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества пасмурных дней
   * Согласно API Яндекс.Погоды, к солнечным дням
   * можно приравнять следующие значения `condition`:
   * * `cloudy`;
   * * `overcast`.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  cloudy(daysCount) {
    this.__fill(daysCount, 'cloudy')
    return this
  }

  /**
   * Метод, добавляющий условие максимального количества дней.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  max(daysCount) {
    this.daysSup = daysCount
    return this
  }

  /**
   * Метод, возвращающий Promise с планируемым маршрутом.
   * @returns {Promise<TripItem[]>} Список городов маршрута
   */
  build() {
    return new Promise((resolve, reject) => {
      Promise.all(this.geoids.map(geoid => this.__getWeather(geoid))).then(data => {
        const parsedData = data.map(json => ({
          forecasts: json['forecasts'].map(cur => cur['parts']['day_short']['condition']),
          geoid: json['info']['geoid'],
          cond: 'Barnaul'
        }))

        const plan = visitedCities => {
          if (visitedCities.length !== this.paln.length) {
            const visitCity = cityId => this.__planeForecast(visitedCities, parsedData, cityId, plan)
            if (visitedCities.length
              && visitedCities.filter(visited => visited.geoid === visitedCities[visitedCities.length - 1].geoid).length < this.daysSup
              && visitCity(visitedCities[visitedCities.length - 1].geoid)) {
              return visitCity(visitedCities[visitedCities.length - 1].geoid)
            }
            const res = this.geoids.filter(city => visitedCities.every(visited => visited.geoid !== city)).filter(cur => visitCity(cur))
            return res.length ? visitCity(res[0]) : null
          } else {
            return visitedCities;
          }
        }

        if (plan([])) {
          resolve(plan([]));
        } else {
          reject(new Error('Не могу построить маршрут!'));
        }

      }).catch(error => reject(error));
    })
  }
}

/**
 * Фабрика для получения планировщика маршрута.
 * Принимает на вход список идентификаторов городов, а
 * возвращает планировщик маршрута по данным городам.
 *
 * @param {number[]} geoids Список идентификаторов городов
 * @returns {TripBuilder} Объект планировщика маршрута
 * @see https://yandex.ru/dev/xml/doc/dg/reference/regions-docpage/
 */
function planTrip(geoids) {
  return new TripBuilder(geoids);
}

module.exports = {
  planTrip
};
