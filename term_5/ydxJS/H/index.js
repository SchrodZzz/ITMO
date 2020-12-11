const AUTOCOMPLETE_BLOCK = 'autocomplete-items'
const AUTOCOMPLETE_ITEM = `autocomplete-items__item`
const AUTOCOMPLETE_ITEM_ACTIVE = `autocomplete-items__item_active`
const AUTOCOMPLETE_CODE = `autocomplete-items__item-code`
const AUTOCOMPLETE_AIRPORT = `autocomplete-items__airport`
const AUTOCOMPLETE_AIRPORT_IN_CITY = `autocomplete-items__airport_in-city`
const AUTOCOMPLETE_LABEL_ACTIVE = `autocomplete__label_active`

const DELAY = 1000

autocomplete(document.getElementById('from'))
autocomplete(document.getElementById('to'))

function autocomplete(input) {
    let prevItem
    let currentItem

    const closeListClickHandler = function (event) {
        closeAllLists(event.target)
    }

    const selectClickHandler = function (event) {
        input.value = event.target.closest('div').querySelector('input').value
        closeAllLists()
    }

    const inputControlHandler = async function () {
        const value = this.value

        closeAllLists()
        if (!value.trim()) {
            this.parentNode.querySelector('label').classList.remove(AUTOCOMPLETE_LABEL_ACTIVE)

            return
        }
        this.parentNode.querySelector('label').classList.add(AUTOCOMPLETE_LABEL_ACTIVE)

        currentItem = -1

        const items = document.createElement('DIV')

        items.classList.add(AUTOCOMPLETE_BLOCK)
        this.parentNode.appendChild(items)

        items.addEventListener('click', selectClickHandler)

        console.log('Cur value to search -> ' + value)
        const airports = await fetch(
            `https://places.aviasales.ru/v2/places.json?locale=ru&max=7&term=${value}&types[]=city&types[]=airport`
        ).then(response => response.json())

        let lastCity = null

        for (const {name, code, country_name: country, city_name: city, type} of airports) {
            const item = document.createElement('div')
            const props = {name, code}

            item.classList.add(AUTOCOMPLETE_ITEM)

            if (type === 'city') {
                lastCity = name
                props.cityName = country
            } else {
                item.classList.add(AUTOCOMPLETE_AIRPORT)
                if (lastCity && city === lastCity) {
                    item.classList.add(AUTOCOMPLETE_AIRPORT_IN_CITY)
                } else {
                    props.cityName = city
                }
            }

            item.innerHTML = `<strong>${props.name}</strong>`

            item.innerHTML += !props.cityName ? `` : `, ${props.cityName}`
            item.innerHTML += `<input type="hidden" value="${props.name}">`
            item.innerHTML += `<span class='${AUTOCOMPLETE_CODE}'>${props.code}</span>`

            items.appendChild(item)
        }
    }

    const saveInputControlHandler = function () {
        let isThrottled = false
        let savedArgs
        let savedThis

        function wrapper() {
            if (isThrottled) {
                savedArgs = arguments
                savedThis = this

                return
            }

            inputControlHandler.apply(this, arguments).then()

            isThrottled = true

            setTimeout(function () {
                isThrottled = false

                if (savedArgs) {
                    wrapper.apply(savedThis, savedArgs)
                    savedArgs = null
                    savedThis = null
                }
            }, DELAY)
        }

        return wrapper
    }

    const arrowsControlHandler = function (event) {
        let items = document.querySelector(`.${AUTOCOMPLETE_BLOCK}`)

        if (items) {
            items = items.getElementsByTagName('div')
        }

        switch (event.code) {
            case 'ArrowDown': {
                prevItem = currentItem
                currentItem += 1
                if (items) {
                    addActive(items)
                }
                break
            }
            case 'ArrowUp': {
                event.preventDefault()
                prevItem = currentItem
                currentItem -= 1
                if (items) {
                    addActive(items)
                }
                break
            }
            case 'Enter': {
                event.preventDefault()
                if (currentItem >= 0) {
                    if (items) {
                        items[currentItem].click()
                    }
                }
                break
            }
        }
    }

    function addActive(items) {
        if (currentItem >= items.length) {
            currentItem = 0
        } else if (currentItem < 0) {
            prevItem = 0
            currentItem = items.length - 1
        }

        if (prevItem >= 0) {
            items[prevItem].classList.remove(AUTOCOMPLETE_ITEM_ACTIVE)
        }

        items[currentItem].classList.add(AUTOCOMPLETE_ITEM_ACTIVE)
        input.value = items[currentItem].querySelector('input').value
    }

    function closeAllLists(target) {
        const items = document.querySelector(`.${AUTOCOMPLETE_BLOCK}`)
        if (items !== null && target !== items) {
            items.removeEventListener('click', selectClickHandler)
            items.outerHTML = ''
        }
    }

    input.addEventListener('input', saveInputControlHandler())
    input.addEventListener('keydown', arrowsControlHandler)

    document.addEventListener('click', closeListClickHandler)
}

function notImplementedYet() {
    alert('Functionality is not implemented yet.\nContact developers for more information.')
}