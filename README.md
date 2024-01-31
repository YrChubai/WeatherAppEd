# Wether APP

## Описание
Android-Приложение написанное на Котлин, которое обращается к Api и показывает погоду по выбранным данным

* При открытии приложения пользователь видит главный экран, который показывает информацию по текущему дню. Температуру сейчас, минимальную и максимальную, город и состояние погоды, последнее обновление, снизу в списке показан прогноз по часам
* Пользователь может выбрать другой день переключившись на экран "Days", при тапе по элементу списка ему покажется прогноз на данный день
* Если GPS выключено, то приложение показывает сообщение о том, что следует его включить для дальнейшей работы
* Информацию можно обновить нажав на соответсвующую кнопку справа

**Начальный экран приложения**
# <img src="https://github.com/YrChubai/WeatherAppEd/blob/master/pic/Screen1.png" height="500">

**Перемещение между "Hours" и "Days"**
# <img src="https://github.com/YrChubai/WeatherAppEd/blob/master/pic/Screenshot2-3.png" height="500">

**Смена дня**
# <img src="https://github.com/YrChubai/WeatherAppEd/blob/master/pic/Screenshot10-11.png" height="500">

**Поиск по городу**
# <img src="https://github.com/YrChubai/WeatherAppEd/blob/master/pic/Screenshot4-6.png" height="500">

**Обновление данных о местоположении**
# <img src="https://github.com/YrChubai/WeatherAppEd/blob/master/pic/Screenshot12-13.png" height="500">

**Запрос на получение местоположение у пользователя**
# <img src="https://github.com/YrChubai/WeatherAppEd/blob/master/pic/Screenshot7-10.png" height="500">

## Стек
* Kotlin
* Picasso
* Retrofit2
* OkHttp3
## Установка
Скопируйте следующий код в Git Bash:
```
$ git clone https://github.com/YrChubai/WeatherAppEd.git
```
Зарегистрируйтесь на сайте [WetherAPI](https://www.weatherapi.com/my/).
> [!IMPORTANT]
> * Поменять API ключ в проекте на своей после регистрации на сайте
> * Путь app/src/main/java/com/example/weatherapped/fragments/MainFragment.kt
> ```kotlin
> const val API_KEY = "YOUR API KEY"
> ```