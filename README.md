# Parking
Приложение, эмулирующее автомобильную парковку. Парковка вмещает конечное число автомобилей.
Площадь проездов между рядами, а также расстояние между местами не учитываем. 
Принимаем, что вся площадь парковки занята местами, между которыми автомобили перемещаются методом телепортации. 
Автомобили могут быть двух типов: легковые(занимают одно место) и грузовики(занимают 2 места). 

Рабочий процесс парковки: при запуске приложения пользователь вводит следующие параметры:
- количество парковочных мест
- максимальную длину очереди автомобилей ожидающих въезда на парковку
- интервал генерации входящих автомобилей в секундах
- интервал генерации выходящих автомобилей в секундах

После введения параметров приложение начинает генерировать входные автомобили и помещать их в очередь. 
Тип автомобиля выбирается произвольно, но с учётом того, что грузовых должно быть меньше. Иными словами, грузовики генерируются реже.
Для частоты генерации используется заданный пользователем интервал. То есть,  если ли мы имеем интервал T1, то в какой-то рандомный момент времени между этим интервалом генерируется автомобиль во входную очередь.
Аналогично происходит удаление автомобилей с парковочных мест. То есть, если мы имеем интервал для удаления авто с парковки T2, то в произвольный момент между этим интервалом выбирается произвольный автомобиль с парковки и удаляется. 
Каждый автомобиль имеет уникальный id. Он может быть любого типа, главное — уникальность. 
Добавление авто во входную очередь и выезд с парковки должны происходить независимо друг от друга. 
Каждые 5 секунд парковка сообщает свою статистику в виде:
- Свободных мест: X
- Занято мест: Y(из них столько-то легковых и столько-то грузовых авто)
- Автомобилей, ожидающих в очереди: Z

Информирование о событиях:
При добавлении автомобиля в очередь парковка сообщает «Легковой/грузовой автомобиль с id = <айдишник авто> встал в очередь на въезд.»
При перемещении авто на парковку парковка сообщает «Легковой/грузовой автомобиль с id = <айдишник авто> припарковался.»
При выезде авто с парковки она сообщает об этом «Легковой/грузовой автомобиль с id = <айдишник авто> покинул парковку.»

Как парковка себя ведёт в разных ситуациях:
- Если мест не осталось, то генерация во входную очередь не прекращается.
- Если входная очередь достигла заданного максимума, то происходит carmageddon и парковка завершает работу с каким-нибудь «alarm» - сообщением. Выход из приложения.
