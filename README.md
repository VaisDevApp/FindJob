
# FindJob

**FindJob** - это тестовое задание, которое реализовано на основе технического задания "Разработка приложения для поиска работы" и макета Figma.

Требовалось реализовать следующие экраны:
 - вкладка **"Главный экран"** с нижним меню. Две активные вкладки "Поиск" и "Избранное", остальные заглушки;
 - вкладка **"Поиск"** содержит следующий функционал. Для загрузки данных выполняем сетевой запрос, данные кэшируются в базу данных. Первоначально отображается только 3 вакансии, при нажатии "Еще 6 вакансий" переходим на следующий фрагмент;
 - во вкладке **"Избранное"** отображаются избранные вакансии, которые можно добавлять с главного экрана и также проваливаться на экран с описанием к этой вакансии (заглушка). 

Для реализации задания использовался следующий стек технологий Android:
   - Kotlin;
   - Coroutines/Flow;
   - Dagger;
   - MVVM;
   - верстка обычная на XML;
   - Clean Architecture;
   - многомодульность;
   - Room;
   - Retrofit.
