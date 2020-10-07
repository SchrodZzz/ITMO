'use strict';

const week = ['ПН', 'ВТ', 'СР', 'ЧТ', 'ПТ', 'СБ', 'ВС']

/**
 * @param {Object} schedule Расписание Банды
 * @param {number} duration Время на ограбление в минутах
 * @param {Object} workingHours Время работы банка
 * @param {string} workingHours.from Время открытия, например, "10:00+5"
 * @param {string} workingHours.to Время закрытия, например, "18:00+5"
 * @returns {Object}
 */
function getAppropriateMoment(schedule, duration, workingHours) {

    const mainTimeZone = parseInt(parseTime(workingHours.from)[3])

    const formattedSchedule = {}
    Object.keys(schedule).forEach(man => {
        formattedSchedule[man] = schedule[man].map(uncomfyTime => {
            return {
                from: {
                    day: uncomfyTime.from.split(' ')[0],
                    time: formatTime(parseTime(uncomfyTime.from.split(' ')[1]), mainTimeZone)
                },
                to: {
                    day: uncomfyTime.to.split(' ')[0],
                    time: formatTime(parseTime(uncomfyTime.to.split(' ')[1]), mainTimeZone)
                }
            }
        })
    })

    const formattedMainTime = {
        from: formatTime(parseTime(workingHours.from), parseInt(parseTime(workingHours.from)[3])),
        to: formatTime(parseTime(workingHours.to), parseInt(parseTime(workingHours.to)[3]))
    }

    const tmp = {}
    for (const day of week) {
        tmp[day] = []
    }
    Object.keys(formattedSchedule).forEach(man => {
        formattedSchedule[man].forEach(({from, to}) => {
            if (from.day !== to.day) {
                tmp[from.day].push({from: from.time, to: formattedMainTime.to})
                tmp[to.day].push({from: formattedMainTime.from, to: to.time})
            } else {
                tmp[from.day].push({from: from.time, to: to.time})
            }
        })
    })
    const daysRanges = {}
    for (let i = 0; i < 3; i++) {
        daysRanges[week[i]] = tmp[week[i]]
    }

    const robberyTime = {}
    for (let i = 0; i < 3; ++i) {
        robberyTime[week[i]] = []
    }
    for (let i = formattedMainTime.from; i <= formattedMainTime.to - duration; ++i) {
        const robberyRange = {from: i, to: i + duration}
        Object.keys(daysRanges).forEach(day => {
            if (daysRanges[day].length) {
                if (isCorrectTime(daysRanges[day], robberyRange)) {
                    robberyTime[day].push(robberyRange)
                }
            } else {
                robberyTime[day].push(robberyRange)
            }
        })
    }

    let closestTime = null
    for (const day of Object.keys(robberyTime)) {
        if (robberyTime[day].length) {
            closestTime = {day: day, range: robberyTime[day][0]}
            break
        }
    }

    return {
        /**
         * Найдено ли время
         * @returns {boolean}
         */
        exists() {
            for (const day of Object.keys(robberyTime)) {
                if (robberyTime[day].length) {
                    return true
                }
            }
            return false
        },

        /**
         * Возвращает отформатированную строку с часами
         * для ограбления во временной зоне банка
         *
         * @param {string} template
         * @returns {string}
         *
         * @example
         * ```js
         * getAppropriateMoment(...).format('Начинаем в %HH:%MM (%DD)') // => Начинаем в 14:59 (СР)
         * ```
         */
        format(template) {
            if (closestTime === null) {
                return ''
            }
            const hrs = formatToTime(parseInt(closestTime.range.from / 60))
            const mns = formatToTime(parseInt(closestTime.range.from % 60))
            return template
                .replace('%HH', hrs)
                .replace('%MM', mns)
                .replace('%DD', closestTime.day)
        },

        /**
         * Попробовать найти часы для ограбления позже [*]
         * @note Не забудь при реализации выставить флаг `isExtraTaskSolved`
         * @returns {boolean}
         */
        tryLater() {
            if (!this.exists()) {
                return false;
            }

            let nextTime = closestTime
            const days = Object.keys(robberyTime);
            const currentDay = closestTime.day;
            for (const day of days) {
                const filtered = robberyTime[day].filter(range => range.from >= (closestTime.range.from + 30));
                if (filtered.length && day === currentDay) {
                    nextTime = {day: currentDay, range: filtered[0]};
                    break
                } else if (days.indexOf(day) > days.indexOf(currentDay) && robberyTime[day].length) {
                    nextTime = {day: day, range: robberyTime[day][0]};
                    break
                }
            }

            if (nextTime.day === closestTime.day && nextTime.range.from === closestTime.range.from) {
                return false;
            } else {
                closestTime = nextTime;
                return true;
            }
        }
    };
}

function parseTime(time) {
    return /^(\d\d):(\d\d)\+(\d)$/.exec(time) || /^(\d\d):(\d\d)\+(\d\d)$/.exec(time)
}

function formatTime(date, mainTimeZone) {
    return (parseInt(date[1]) * 60 + parseInt(date[2])) + ((mainTimeZone - parseInt(date[3])) * 60)
}

function isCorrectTime(dayBusyRanges, robberyRange) {
    for (const manTime of dayBusyRanges) {
        if (!((robberyRange.from < manTime.from && robberyRange.to <= manTime.from) ||
            (robberyRange.from >= manTime.to && robberyRange.to > manTime.to))) {
            return false
        }
    }
    return true
}

function formatToTime(num) {
    const res = num.toString()
    return res.length !== 1 ? res : `0${res}`
}

module.exports = {
    getAppropriateMoment
};