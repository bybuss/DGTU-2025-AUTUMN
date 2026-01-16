# Ростелеком Аналитика

Другие репозитории:
- [Frontend](https://github.com/MissClickRND/HackRND2025-Autumn)
- [Backend](https://github.com/MissClickRND/HackRND2025-Autumn-Backend)
- [Mobile](https://github.com/bybuss/DGTU-2025-AUTUMN)

---

## Оглавление
- [Описание](#описание)
- [Структура и особенности кода](#структура-и-особенности-кода)
- [Скриншоты](#скриншоты)    
- [Используемые технологии](#используемые-технологии)
- [Установка](#установка)
- [Портфолио](#портфолио)

## Описание
**Ростелеком Аналитика** — Android приложение, разработанное на XVII Форуме программных разработчиков Ростова-на-Дону «Хакатон ОСЕНЬ 2025» в рамках кейса от Ростелеком «Приложение для управления проектами с веб-интерфейсом и прогностическо-аналитическим модулем».

### Основные функции
1. Анимированный Splash Screen с логотипом Ростелеком.
2. Авторизация и регистрация пользователя с валидацией полей и ожиданием верификации админом из админ-панели.
4. Страница дашбордов с различными графиками с приятными анимациями.
5. Возможность создания проектов с возможностью редактивания деталей.
6. Таблица неверифицированных пользователей с приятной анимайией свайпа для удаления/верификации и таблица всех пользователей в админ-панеле.
7. Таблица бэкапов с детальной информацией.
8. Список справочников с подпунктакми, возможностью добавления и подпунктов и поиском по названию. 

## Структура и особенности кода
Проект использует паттерн **MVI**. Каждая функциональность оформлена отдельным пакетом (`admin_panel`, `analytics`, `auth`, `onboarding`, `projects`, `reports`, `waiting_verification`) с подпакетами `data`, `domain` и `presentation` по чистой архитектуре.

- **Навигация** построена через `Navigation Compose` и типобезопасные маршруты. Состояния экранов описаны как сериализуемые sealed классы в [`Screens`](app/src/main/java/bob/colbaskin/webantpractice/navigation/Screens.kt), графы объявлены в [`Graphs`](app/src/main/java/bob/colbaskin/webantpractice/navigation/Graphs.kt), а переходы выполняются с помощью расширения [`animatedTransition`](app/src/main/java/bob/colbaskin/webantpractice/navigation/animatedTransition.kt).
- **Данные пользователя** хранятся в `DataStore` с `ProtoBuf` ([`UserDataStore`](app/src/main/java/bob/colbaskin/webantpractice/common/user_prefs/data/local/datastore/UserDataSore.kt)), что обеспечивает type‑safe сохранение статуса онбординга и авторизации.
- **DI** реализовано через **Hilt**. Модули располагаются в пакете [`di`](app/src/main/java/bob/colbaskin/webantpractice/di) и предоставляют `Retrofit`, `OkHttpClient` с перехватчиками токенов и репозитории для работы с API.
- **Дизайн‑система** включает собственную тему [`WebAntPracticeTheme`](app/src/main/java/bob/colbaskin/webantpractice/common/design_system/theme/Theme.kt), палитру цветов, шейпы и типографику. Все компоненты интерфейса из [UiKit'а](https://www.figma.com/design/90x9N8TsvSJLx9eecN2zGr/iOS-Тестовое-Gallery?node-id=12685-2013&p=f&t=Ib96xtaazHErKtDj-0) (`AppBars`, `Buttons`, `CustomTextField` и др. также необходимые по макету)  переиспользуются во всех экранах.
- Все строки и ресурсы вынесены в [`strings.xml`](app/src/main/res/values/strings.xml) и обращение к ним происходит через `R.string.*`, что упрощает локализацию.
- Для загрузки изображений используется **Coil**, а сетевые запросы выполняются через **Retrofit** с логированием и хранением cookies.
- **Secrets Plugin** используется для сокрытия конфиденциальной информации в `local.properties`. Для запуска будет необходимо указать нужные данные: `BASE_API_URL`.
  Пример файла `local.properties`, если захотите скачать и запустить проект:
```properties
    sdk.dir=...
    BASE_API_URL=https://example.api/
```

## Скриншоты
<p align="center">
  <img src="screenshots/1.png" width="125" />
  <img src="screenshots/2.png" width="125" />
  <img src="screenshots/3.png" width="125" />
  <img src="screenshots/4.png" width="125" />
  <img src="screenshots/5.png" width="125" />
  <img src="screenshots/6.png" width="125" />
  <img src="screenshots/7.png" width="125" />
  <img src="screenshots/8.png" width="125" />
  <img src="screenshots/8-9anim.gif" width="125" />
  <img src="screenshots/9.png" width="125" />
  <img src="screenshots/10.png" width="125" />
  <img src="screenshots/11.png" width="125" />
  <img src="screenshots/12.png" width="125" />
  <img src="screenshots/12-13anim.gif" width="125" />
  <img src="screenshots/13.png" width="125" />
  <img src="screenshots/14.png" width="125" />
  <img src="screenshots/15.png" width="125" />
</p>

## Используемые технологии
| Технология                    | Описание                                                       |
|-------------------------------|----------------------------------------------------------------|
| **Jetpack Compose**           | Построение пользовательского интерфейса и навигации            |
| **Hilt**                      | Внедрение зависимостей                                         |
| **Retrofit + OkHttp**         | Работа с HTTP API и перехват запросов                          |
| **DataStore (ProtoBuf)**      | Хранение настроек и токенов пользователя                       |
| **Paging**                    | Постраничная подгрузка списка фотографий                       |
| **Kotlinx Serialization**     | Сериализация аргументов для type‑safe навигации + API запросов |
| **Coil**                      | Загрузка изображений                                           |
| **Secrets Plugin**            | Сокрытие конфиденциальной информации                           |

## Установка

<p align="center">Ссылка на текущую версию <a href="https://github.com/bybuss/WebAntPractice/releases/tag/v1.0.0">Releases</a></p>
<p align="center"><a href="https://github.com/user-attachments/files/20852622/apk.zip">Прямая ссылка на установку .zip</a></p>
<p align="center"><a href="https://github.com/bybuss/WebAntPractice/blob/main/app/release/app-release.apk">Прямая ссылка на установку .apk</a></p>


## Портфолио
Для ознакомления с другими работами автора вы можете посетить портфолио на [Behance](https://www.behance.net/gallery/222004489/Android-Developer-Portfolio).
