/**
 * Возвращает новый emitter
 * @returns {Object}
 */
function getEmitter() {

    let events = []

    let eventPredicate = (cur, event) => cur === event || cur.startsWith(`${event}.`)
    let contextPredicate = (man, context) => man.context !== context

    return {

        parse: function (event) {
            return event.split('.').reduce((res, cur) => res.length === 0 ? [cur] : res.concat([`${res[0]}.${cur}`]).reverse(), [])
        },

        /**
         * Подписаться на событие
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         */
        on: function (event, context, handler) {
            const toAddElement = {context, handler};
            (event in events) ? events[event].push(toAddElement) : events[event] = [toAddElement]
            return this
        },

        /**
         * Отписаться от события
         * @param {String} event
         * @param {Object} context
         */
        off: function (event, context) {
            Object.keys(events)
                .filter(cur => eventPredicate(cur, event))
                .forEach(key => {
                    events[key] = events[key].filter(man => contextPredicate(man, context))
                })
            return this
        },

        /**
         * Уведомить о событии
         * @param {String} event
         */
        emit: function (event) {
            this.parse(event)
                .filter(cur => cur in events)
                .forEach(key => events[key].forEach(person => person.handler.call(person.context)))
            return this
        },

        /**
         * Подписаться на событие с ограничением по количеству полученных уведомлений
         * @star
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         * @param {Number} times – сколько раз получить уведомление
         */
        several: function (event, context, handler, times) {
            let callCounter = 0
            this.on(event, context, times > 0 ? () => {
                callCounter++ < times ? handler.call(context) : callCounter--
            } : handler)
            return this
        },

        /**
         * Подписаться на событие с ограничением по частоте получения уведомлений
         * @star
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         * @param {Number} frequency – как часто уведомлять
         */
        through: function (event, context, handler, frequency) {
            let callCounter = 0
            this.on(event, context, frequency > 0 ? () => {
                callCounter++ % frequency === 0 ? handler.call(context) : null
            } : handler)
            return this
        }
    };
}

const isExtraTaskSolved = true

module.exports = {
    getEmitter,
    isExtraTaskSolved
};