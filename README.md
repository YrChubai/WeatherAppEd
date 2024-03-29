# Wether APP

## Описание
Android-Приложение написанное на Котлин, которое обращается к Api и показывает погоду по выбранным данным

* При открытии приложения пользователь видит главный экран, который показывает информацию по текущему дню. Температуру сейчас, минимальную и максимальную, город и состояние погоды, последнее обновление, снизу в списке показан прогноз по часам
* Пользователь может выбрать другой день переключившись на экран "Days", при тапе по элементу списка ему покажется прогноз на данный день
* Если GPS выключено, то приложение показывает сообщение о том, что следует его включить для дальнейшей работы
* Информацию можно обновить нажав на соответсвующую кнопку справа

**Начальный экран приложения**
# <img src="https://github.com/YrChubai/WeatherAppEd/assets/107373880/985eb4f8-b3c3-4305-8c38-aabd6b3513c0" height="500">

**Перемещение между "Hours" и "Days"**
# <img src="https://github.com/YrChubai/WeatherAppEd/assets/107373880/62a1fcae-4351-4c39-b18e-41d0c39c354d" height="500">

**Смена дня**
# <img src="https://github.com/YrChubai/WeatherAppEd/assets/107373880/d2abff51-e342-4b8b-9a71-e2a48cb80bf2" height="500">

**Поиск по городу**
# <img src="https://github.com/YrChubai/WeatherAppEd/assets/107373880/d449975d-c98c-4659-aa32-f1cc5f937cee" height="500">

**Обновление данных о местоположении**
# <img src="https://github.com/YrChubai/WeatherAppEd/assets/107373880/cde6fa43-206f-459f-bbaf-8ccdef05d22f" height="500">

**Запрос на получение местоположение у пользователя**
# <img src="https://github.com/YrChubai/WeatherAppEd/assets/107373880/870b44eb-a97a-49f4-b50a-3d5f0632c8b9" height="500">

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