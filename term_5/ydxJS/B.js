'use strict';

/**
 * Телефонная книга
 */
const phoneBook = new Map();

/**
 * Вызывайте эту функцию, если есть синтаксическая ошибка в запросе
 * @param {number} lineNumber – номер строки с ошибкой
 * @param {number} charNumber – номер символа, с которого запрос стал ошибочным
 */
function syntaxError(lineNumber, charNumber) {
    throw new Error(`SyntaxError: Unexpected token at ${lineNumber}:${charNumber}`);
}

/**
 * Выполнение запроса на языке pbQL
 * @param {string} query
 * @returns {string[]} - строки с результатами запроса
 */
function run(query) {
    let results = []

    const commands = query.split(';')
    commands.forEach((command) => {
        results = processCommand(command.split(' '), commands, command, results)
    })
    if (commands[commands.length - 1] !== '') {
        syntaxError(commands.length, commands[commands.length - 1].length + 1)
    }

    return results
}

function processCommand(words, commands, command, result) {
    switch (words[0]) {
        case 'Создай': {
            processCreate(words, commands, command)
            return result
        }
        case 'Удали': {
            processDelete(words, commands, command)
            return result
        }
        case 'Добавь': {
            processAdd(words, commands, command)
            return result
        }
        case 'Покажи': {
            return result.concat(processShow(words, commands, command))
        }
        default: {
            if (commands.indexOf(command) !== commands.length - 1 || command !== '') {
                syntaxError(commands.indexOf(command) + 1, 1)
            }
            return result
        }
    }
}

function processCreate(words, commands, command) {
    if (words[1] === 'контакт') {
        if (!phoneBook.has(words.slice(2).join(' '))) {
            phoneBook.set(words.slice(2).join(' '), {phone: [], email: []})
        }
    } else {
        syntaxError(commands.indexOf(command) + 1, words[0].length + 2)
    }
}

function processDelete(words, commands, command) {
    if (words[1] === 'контакт') {
        phoneBook.delete(words.slice(2).join(' '))
    } else if (words[1] === 'контакты,') {
        if (words[2] !== 'где') {
            syntaxError(commands.indexOf(command) + 1, getErrIdx(words, 1) + 1)
        }
        if ((words[3]) !== 'есть') {
            syntaxError(commands.indexOf(command) + 1, getErrIdx(words, 2) + 1)
        }
        const req = words.slice(4).join(' ')
        if (req !== '') {
            for (const contacts of phoneBook) {
                if (contacts[0].includes(req) ||
                    contacts[1].phone.some(phone => phone.includes(req)) ||
                    contacts[1].email.some(email => email.includes(req))) {
                    phoneBook.delete(contacts[0])
                }
            }
        }
    } else {
        helper(words, commands, command, true)
    }
}

function processAdd(words, commands, command) {
    helper(words, commands, command, false)
}

function processShow(words, commands, command) {
    let params = words.slice(1, words.indexOf('для'));
    let previousIndex = words.indexOf('для');
    let parsedParams = showProcParse(params, commands, command, words);
    if (params[params.length - 1] === 'и') {
        syntaxError(commands.indexOf(command) + 1, getErrIdx(words, 0) + 1);
    }
    if (words[previousIndex + 1] !== 'контактов,') {
        syntaxError(commands.indexOf(command) + 1, getErrIdx(words, previousIndex) + 1);
    }
    if (words[previousIndex + 2] !== 'где') {
        syntaxError(commands.indexOf(command) + 1, getErrIdx(words, previousIndex + 1) + 1);
    }
    if (words[previousIndex + 3] !== 'есть') {
        syntaxError(commands.indexOf(command) + 1, getErrIdx(words, previousIndex + 2) + 1);
    }

    return getInfo(words.slice(previousIndex + 4).join(' '), parsedParams) || [];
}

function getInfo(request, list) {
    let result = [];
    if (request === '') {
        return;
    }
    let tmp = [];
    for (const contacts of phoneBook) {
        if (contacts[0].includes(request) ||
            contacts[1].email.some(email => email.includes(request)) ||
            contacts[1].phone.some(phone => phone.includes(request))) {
            tmp = [];
            for (const elem of list) {
                switch (elem) {
                    case 'почты':
                        tmp.push(contacts[1].email.join(','));
                        break;
                    case 'имя':
                        tmp.push(contacts[0]);
                        break;
                    case 'телефоны':
                        let formattedPhones = [];
                        for (const phone of contacts[1].phone) {
                            formattedPhones.push(phone.replace(/(\d{3})(\d{3})(\d{2})(\d{2})/, '+7 ($1) $2-$3-$4'));
                        }
                        tmp.push(formattedPhones.join(','));
                        break;
                    default:
                        return;
                }
            }
        }
        if (tmp.length !== 0) {
            result.push(tmp.join(';'));
        }
        tmp = [];
    }
    return result;
}


function getErrIdx(words, idx) {
    return words.slice(0, idx+1).reduce((sum, cur) => sum.length + cur.length + 1) + 1
}

function helper(words, commands, command, isDelete) {
    let params = words.slice(1, words.indexOf('для'))
    let prvIdx = words.indexOf('для')
    const data = parse(params, commands, command, words)

    if (words[prvIdx + 1] !== 'контакта') {
        syntaxError(commands.indexOf(command) + 1, getErrIdx(words, prvIdx) + 1)
    }

    const mails = data[0]
    const phones = data[1]
    const contact = phoneBook.get(words.slice(prvIdx + 2).join(' '))
    if (typeof contact !== 'undefined') {
        updateInfo(mails, contact, 'email', isDelete)
        updateInfo(phones, contact, 'phone', isDelete)
    }
}

function updateInfo(list, contact, type, isDelete) {
    for (const val of list) {
        if (isDelete && contact[type].includes(val)) {
            contact[type].splice(contact[type].indexOf(val), 1)
        } else if (!isDelete && !contact[type].includes(val)) {
            contact[type].push(val)
        }
    }
}

function parse(params, commands, command, words) {
    let phoneList = []
    let mailList = []
    let curSym = 1
    for (let i = 0; i < params.length; i++) {
        if (params[i] === 'почту' && curSym % 2 === 1) {
            if (i + 1 === params.length) {
                syntaxError(commands.indexOf(command) + 1, getErrIdx(words, i + 1) + 1)
            }
            mailList.push(params[i + 1]);
            curSym++;
            i++;
        } else if (params[i] === 'телефон' && curSym % 2 === 1) {
            if (i + 1 === params.length || !params[i + 1].match(/^\d{10}$/)) {
                syntaxError(commands.indexOf(command) + 1, getErrIdx(words, i + 1) + 1);
            }
            phoneList.push(params[i + 1]);
            curSym++;
            i++;
        } else if (params[i] === 'и' && curSym % 2 === 0) {
            curSym++
        } else {
            syntaxError(commands.indexOf(command) + 1, getErrIdx(words, i) + 1)
        }
    }
    if (params[params.length - 1] === 'и') {
        syntaxError(commands.indexOf(command) + 1, getErrIdx(words, params.length - 1) + 1);
    }
    return [mailList, phoneList];
}

function showProcParse(params, commands, command, words) {
    let res = [];
    let curSym = 1;
    for (const val of params) {
        if (['имя', 'почты', 'телефоны'].includes(val) && curSym % 2 === 1) {
            res.push(val);
            curSym++;
        } else if (val === 'и' && curSym % 2 === 0) {
            curSym++;
        } else {
            syntaxError(commands.indexOf(command) + 1, getErrIdx(words, curSym - 1) + 1);
        }
    }
    return res;
}

module.exports = {phoneBook, run};


// console.log(1, run('Покажи имя для контактов, где есть ий;'))

// console.log(2, run(
//     'Создай контакт Григорий;' +
//     'Создай контакт Василий;' +
//     'Создай контакт Иннокентий;' +
//     'Покажи имя для контактов, где есть ий;'
// ))

// console.log(3, run(
//     'Создай контакт Григорий;' +
//     'Создай контакт Василий;' +
//     'Создай контакт Иннокентий;' +
//     'Покажи имя и имя и имя для контактов, где есть ий;'
// ))

// console.log(4, run(
//     'Создай контакт Григорий;' +
//     'Покажи имя для контактов, где есть ий;' +
//     'Покажи имя для контактов, где есть ий;'
// ))

// console.log(5, run(
//     'Создай контакт Григорий;' +
//     'Удали контакт Григорий;' +
//     'Покажи имя для контактов, где есть ий;'
// ))

// console.log(6, run(
//     'Создай контакт Григорий;' +
//     'Добавь телефон 5556667787 для контакта Григорий;' +
//     'Добавь телефон 5556667788 и почту grisha@example.com для контакта Григорий;' +
//     'Покажи имя и телефоны и почты для контактов, где есть ий;'
// ))

// console.log(7, run(
//     'Создай контакт Григорий;' +
//     'Добавь телефон 5556667788 для контакта Григорий;' +
//     'Удали телефон 5556667788 для контакта Григорий;' +
//     'Покажи имя и телефоны для контактов, где есть ий;'
// ))