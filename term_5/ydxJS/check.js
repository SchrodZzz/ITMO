'use strict';

const robbery = require('./C');

const gangSchedule = {
    Danny: [{ from: 'ПН 12:00+2', to: 'ПН 17:00+2' }],
    Rusty: [{ from: 'ПН 11:30+9', to: 'ПН 16:30+9' }],
    Linus: [
    { from: 'ПН 09:00+3', to: 'ПН 14:00+3' },
    ]
};

const bankWorkingHours = {
    from: '10:00+1',
    to: '18:00+1'
};

// Время не существует
const longMoment = robbery.getAppropriateMoment(gangSchedule, 200, bankWorkingHours);

// Выведется `false` и `""`
console.info(longMoment.exists());
console.info(longMoment.format('Метим на %DD, старт в %HH:%MM!'));

// Время существует
const moment = robbery.getAppropriateMoment(gangSchedule, 90, bankWorkingHours);

// Выведется `true` и `"Метим на ВТ, старт в 11:30!"`
console.info(moment.exists());
console.info(moment.format('Метим на %DD, старт в %HH:%MM!'));

// Дополнительное задание
// Вернет `true`
moment.tryLater();
// `"ВТ 16:00"`
console.info(moment.format('%DD %HH:%MM'));

// Вернет `true`
moment.tryLater();
// `"ВТ 16:30"`
console.info(moment.format('%DD %HH:%MM'));

// Вернет `true`
moment.tryLater();
// `"СР 10:00"`
console.info(moment.format('%DD %HH:%MM'));

// Вернет `false`
moment.tryLater();
// `"СР 10:00"`
console.info(moment.format('%DD %HH:%MM'));