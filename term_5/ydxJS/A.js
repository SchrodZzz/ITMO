'use strict';

/**
 * Складывает два целых числа
 * @param {Number} a Первое целое
 * @param {Number} b Второе целое
 * @throws {TypeError} Когда в аргументы переданы не числа
 * @returns {Number} Сумма аргументов
 */
function abProblem(a, b) {
    if (Number.isInteger(a) && Number.isInteger(b)) {
        return a + b
    } else {
        throw new TypeError()
    }
}

/**
 * Определяет век по году
 * @param {Number} year Год, целое положительное число
 * @throws {TypeError} Когда в качестве года передано не число
 * @throws {RangeError} Когда год – отрицательное значение
 * @returns {Number} Век, полученный из года
 */
function centuryByYearProblem(year) {
    if (Number.isInteger(year)) {
        if (year >= 0) {
            return Math.ceil(year / 100)
        } else {
            throw new RangeError()
        }
    } else {
        throw new TypeError()
    }
}

/**
 * Переводит цвет из формата HEX в формат RGB
 * @param {String} hexColor Цвет в формате HEX, например, '#FFFFFF'
 * @throws {TypeError} Когда цвет передан не строкой
 * @throws {RangeError} Когда значения цвета выходят за пределы допустимых
 * @returns {String} Цвет в формате RGB, например, '(255, 255, 255)'
 */
function colorsProblem(hexColor) {
    if (typeof hexColor === 'string') {
        if ((hexColor.match(/^#[\da-fA-F]{3}$/) || hexColor.match(/^#[\da-fA-F]{6}$/))) {
            const rgb = []
            if (hexColor.length !== 4) {
                rgb[0] = parseInt(hexColor.substr(1, 2), 16)
                rgb[1] = parseInt(hexColor.substr(3, 2), 16)
                rgb[2] = parseInt(hexColor.substr(5, 2), 16)
            } else {
                rgb[0] = parseInt(hexColor[1].repeat(2), 16)
                rgb[1] = parseInt(hexColor[2].repeat(2), 16)
                rgb[2] = parseInt(hexColor[3].repeat(2), 16)
            }
            return `(${rgb[0]}, ${rgb[1]}, ${rgb[2]})`
        } else {
            throw new RangeError()
        }
    } else {
        throw new TypeError()
    }
}

/**
 * Находит n-ое число Фибоначчи
 * @param {Number} n Положение числа в ряде Фибоначчи
 * @throws {TypeError} Когда в качестве положения в ряде передано не число
 * @throws {RangeError} Когда положение в ряде не является целым положительным числом
 * @returns {Number} Число Фибоначчи, находящееся на n-ой позиции
 */
function fibonacciProblem(n) {
    if (typeof n === 'number') {
        if (Number.isInteger(n) && n > 0) {
            let dp = [1, 1]
            if (n < 2) {
                return dp[n - 1]
            } else {
                for (let i = 2; i < n; i++) {
                    dp.push(dp[i - 1] + dp[i - 2])
                }
                return dp[n - 1]
            }
        } else {
            throw new RangeError()
        }
    } else {
        throw new TypeError()
    }
}

/**
 * Транспонирует матрицу
 * @param {(Any[])[]} matrix Матрица размерности MxN
 * @throws {TypeError} Когда в функцию передаётся не двумерный массив
 * @returns {(Any[])[]} Транспонированная матрица размера NxM
 */
function matrixProblem(matrix) {
    const isArray = (arr) => arr.every(el => Array.isArray(el))
    if (isArray(matrix)) {
        const n = matrix.length
        const m = matrix[0].length
        let transArr = buildArr(n, m)
        for (let i = 0; i < n; i++) {
            for (let j = 0; j < m; j++) {
                transArr[j][i] = matrix[i][j]
            }
        }
        return transArr
    } else {
        throw new TypeError()
    }
}


const buildArr = (n, m) => {
    let arr = []
    for (let i = 0; i < m; i++) {
        arr.push([])
    }
    return arr
}

/**
 * Переводит число в другую систему счисления
 * @param {Number} n Число для перевода в другую систему счисления
 * @param {Number} targetNs Система счисления, в которую нужно перевести (Число от 2 до 36)
 * @throws {TypeError} Когда переданы аргументы некорректного типа
 * @throws {RangeError} Когда система счисления выходит за пределы значений [2, 36]
 * @returns {String} Число n в системе счисления targetNs
 */
function numberSystemProblem(n, targetNs) {
    if (typeof n === 'number' && Number.isInteger(targetNs)) {
        if (targetNs >= 2 && targetNs <= 36) {
            return n.toString(targetNs)
        } else {
            throw new RangeError()
        }
    } else {
        throw new TypeError()
    }
}

/**
 * Проверяет соответствие телефонного номера формату
 * @param {String} phoneNumber Номер телефона в формате '8–800–xxx–xx–xx'
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Boolean} Если соответствует формату, то true, а иначе false
 */
function phoneProblem(phoneNumber) {
    if (typeof phoneNumber === 'string') {
        return /^8-800-\d{3}-\d{2}-\d{2}$/.test(phoneNumber)
    } else {
        throw new TypeError()
    }
}

/**
 * Определяет количество улыбающихся смайликов в строке
 * @param {String} text Строка в которой производится поиск
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Number} Количество улыбающихся смайликов в строке
 */
function smilesProblem(text) {
    if (typeof text === 'string') {
        return (text.match(/(:-\))|(\(-:)/g) || []).length
    } else {
        throw new TypeError()
    }
}

/**
 * Определяет победителя в игре "Крестики-нолики"
 * Тестами гарантируются корректные аргументы.
 * @param {(('x' | 'o')[])[]} field Игровое поле 3x3 завершённой игры
 * @returns {'x' | 'o' | 'draw'} Результат игры
 */
function ticTacToeProblem(field) {
    let winX = false
    let winO = false
    for (let i = 0; i < 3; i++) {
        let cntX = 0
        let cntO = 0
        for (let j = 0; j < 3; j++) {
            if (field[i][j] === "x") {
                cntX += 1
            } else {
                cntO += 1
            }
        }
        if (cntX === 3) {
            winX = true
        } else if (cntO === 3) {
            winO = true
        }
    }
    for (let i = 0; i < 3; i++) {
        let cntX = 0
        let cntO = 0
        for (let j = 0; j < 3; j++) {
            if (field[j][i] === "x") {
                cntX += 1
            } else {
                cntO += 1
            }
        }
        if (cntX === 3) {
            winX = true
        } else if (cntO === 3) {
            winO = true
        }
    }
    let cntX = 0
    let cntO = 0
    for (let i = 0; i < 3; i++) {
        if (field[i][i] === "x") {
            cntX += 1
        } else {
            cntO += 1
        }
    }
    if (cntX === 3) {
        winX = true
    } else if (cntO === 3) {
        winO = true
    }
    cntX = 0
    cntO = 0
    if (field[0][2] === "x") {
        cntX += 1
    } else {
        cntO += 1
    }
    if (field[1][1] === "x") {
        cntX += 1
    } else {
        cntO += 1
    }
    if (field[2][0] === "x") {
        cntX += 1
    } else {
        cntO += 1
    }
    if (cntX === 3) {
        winX = true
    } else if (cntO === 3) {
        winO = true
    }
    if (winX && !winO) {
        return 'x';
    } else if (winO && !winX) {
        return 'o';
    } else {
        return 'draw';
    }
}

// console.log(abProblem(-123, 1))
// console.log(centuryByYearProblem(2018))
// console.log(colorsProblem('#00'))
// console.log(colorsProblem('#000'))
// console.log(colorsProblem('#00000'))
// console.log(colorsProblem('#000000'))
// console.log(fibonacciProblem(0))
// console.log(matrixProblem([
//     [1, 2, 3],
//     [4, 5, 6],
//     [7, 8, 9],
//     [9, 9, 9]
// ]))
// console.log(numberSystemProblem(15.2, 16))
// console.log(phoneProblem('8-801-333-51-73'))
// console.log(smilesProblem(':-)(-:'))
// console.log(ticTacToeProblem([
//     ['x', 'o', 'o'],
//     ['x', 'o', 'x'],
//     ['o', 'x', 'o']
// ]))

module.exports = {
    abProblem,
    centuryByYearProblem,
    colorsProblem,
    fibonacciProblem,
    matrixProblem,
    numberSystemProblem,
    phoneProblem,
    smilesProblem,
    ticTacToeProblem
};