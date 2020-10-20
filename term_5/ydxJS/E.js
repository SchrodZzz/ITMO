'use strict';

/**
 * Итератор по друзьям
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 */
function Iterator(friends, filter) {
    let list = friends
        .filter(cur => cur.best)
        .sort((a, b) => a.name.localeCompare(b.name))
    const used = new Set()
    this.ppls = []
    let predicate = (a, b) => b === undefined || isNaN(b) ? a.length > 0 : a.length > 0 && b > 0
    while (predicate(list, this.lim)) {
        this.ppls.push(...list)
        this.ppls.forEach(cur => used.add(cur.name))
        const nextNames = list
            .reduce((acc, next) => acc.concat(next.friends), [])
            .filter(name => !used.has(name))

        list = friends.filter(cur => nextNames.includes(cur.name)).sort((a, b) => a.name.localeCompare(b.name))
        this.lim -= 1
    }
    this.ppls = this.ppls.filter(filter.isOk)

    this.done = function () {
        return this.ppls.length === 0
    }

    this.next = function () {
        if (this.done()) {
            return null
        } else {
            return this.ppls.shift()
        }
    }
}

/**
 * Итератор по друзям с ограничением по кругу
 * @extends Iterator
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 * @param {Number} maxLevel – максимальный круг друзей
 */
function LimitedIterator(friends, filter, maxLevel) {
    this.lim = maxLevel
    Iterator.call(this, friends, filter)
}

Object.setPrototypeOf(LimitedIterator.prototype, Iterator.prototype)
Object.setPrototypeOf(MaleFilter.prototype, Filter.prototype)
Object.setPrototypeOf(FemaleFilter.prototype, Filter.prototype)

/**
 * Фильтр друзей
 * @constructor
 */
function Filter() {
    this.isOk = () => true
}

/**
 * Фильтр друзей
 * @extends Filter
 * @constructor
 */
function MaleFilter() {
    return createFilter('male')
}

Object.setPrototypeOf(MaleFilter.prototype, Filter.prototype)

/**
 * Фильтр друзей-девушек
 * @extends Filter
 * @constructor
 */
function FemaleFilter() {
    return createFilter('female')
}

Object.setPrototypeOf(FemaleFilter.prototype, Filter.prototype)


function createFilter(gender) {
    const filter = Object.create(Filter.prototype)
    filter.isOk = (cur => cur.gender === gender)
    return filter
}

exports.Iterator = Iterator;
exports.LimitedIterator = LimitedIterator;

exports.Filter = Filter;
exports.MaleFilter = MaleFilter;
exports.FemaleFilter = FemaleFilter;