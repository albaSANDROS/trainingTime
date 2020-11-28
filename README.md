# trainingTime
 © programm for TRITPO BSUIR, 850503, ALEX BASKO

![alt text](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/main.jpg?raw=true)

***1.Введение***

***Название проекта «trainingTime»***

Данное мобильное приложение будет представлять собой интервальный таймер для тренировок. Он предлагает пользователю следующие возможности:

•	Интуитивный интерфейс: после установки данного таймера пользователю необходимо нажать только одну кнопку, чтобы начать интервальную тренировку.

•	Настраиваемые цвета приложения (тёмная и светлая темы).

•	Настройка шрифтов по размеру.

•	Выбор языка приложения: Русский, Английский.

•	Индивидуальные настройки интервалов: пользователь может создать любую интервальную тренировку с любой последовательностью интервалов.

•	Создавать последовательности тренировок, чтобы они запускались одна за другой.

•	Возможность быстрого перехода с одного этапа на другой.

•	Работа в фоновом режиме, что дает возможность работать, когда экран заблокирован или используется другое приложение (музыкальный плеер, приложение с упражнениями).


***2.	Требования пользователя***

**2.1.	 Приложения является мобильным и будет взаимодействовать исключительно с базой данных.**

**2.2.	 После запуска приложения пользователь увидит интерфейс, состоящий условно из двух областей:**
	
	А. Тренировка;
	
	Б. Настройки приложения.
	
**2.3.	 Данное приложение рассчитано на пользователей любого возраста, любого уровня образования и с минимальным уровнем владение мобильным телефоном. Также стоит отметить, что оно не требует от пользователя знаний в области спорта, спортивных тренировок.**
	
**2.4.	 Так как приложение мобильное и не требует интернет соединения, то единственным ограничение при работе с приложение является доступ к базе данных.**

***3.	Системные требования***

**3.1.	 Функциональные требования:**
	
	А. Возможность создать тренировку;
	
	Б. Изменить тренировку;

	В. Удалить тренировку;

	Г. Повторить тренировку;

	Д. Изменение цвета темы приложения;
	
	Е. Возможность работы на любом смартфоне с операционной системой Android;
	
	Ж. Изменение языка приложения;

	И. Изменение шрифта приложения;


**3.2.	 Нефункциональные требования**
«trainingTime» предложит пользователю интуитивный интерфейс: после установки данного таймера пользователю будет необходимо нажать только одну кнопку, чтобы начать интервальную тренировку. Приложение будет располагаться локально и соответственно, не требует повышенной степени безопасности. Для целостности работы приложения необходима защита базы данных от несанкционированных действий, т.к. удаление информации из базы данных приведет к потере информации о пользователе. 
Приложение будет работать только на мобильных телефонах с операционной системой Android. 


**4. Тестирование**
Тест-кейс № 1. Создание тренировки без этапов.

Шаги:

1.	Открыть приложение.

2.	Выбрать пункт «Добавить» на главном экране.

3.	Назвать тренировку любым названием.

4.	Нажать на кнопку "Сохранить", не добавляя этапов.

Ожидаемый результат

Появляется сообщение об ошибке "Добавьте этапы тренировки", запрет на сохранение тренировки.

Тест-кейс № 2. Создание тренировки без названия.
Шаги:

	1.	Открыть приложение.

	2.	Выбрать пункт «Добавить» на главном экране.

	3.	Добавить любое количество этапов с любыми интервальными отрезками.

	4.	Нажать на кнопку "Сохранить", не добавляя название тренировки.

Ожидаемый результат

Появляется сообщение об ошибке "Напишите название тренировки", запрет на сохранение тренировки.

Тест-кейс № 3. Создание этапа без времени.
Шаги:
	
	1.	Открыть приложение.
	
	2.	Выбрать пункт «Добавить» на главном экране.
	
	3.	Добавить любой этап.
	
	4.	Не указывать время этапа.
	
	5.	Нажать на кнопку «Добавить»
	
	6.	Попытаться сохранить этап.

Ожидаемый результат

Появляется сообщение об ошибке "Введите время этапа", запрет на сохранение тренировки, ошибка после 5-го шага.

Тест-кейс № 4. Создание этапа с отрицательным значением времени.

Шаги:
	
	1.	Открыть приложение.
	
	2.	Выбрать пункт «Добавить» на главном экране.
	
	3.	Добавить любой этап.
	
	4.	Указать любое значение времени от 1 до бесконечности.
	
	5.	Нажать на кнопку «Добавить»
	
	6.	Уменьшить значение времени в отрицательную сторону с помощью кнопки «-» на экране.
	
	7.	Попытаться сохранить этап.

Ожидаемый результат

После достижения значения в «1» уменьшение времени прекращается.

Тест-кейс № 5. Изменение настроек приложения и перезапуск последнего через временной промежуток.

Шаги:

	1.	Открыть приложение.

	2.	Выбрать пункт «Настройки» на главном экране, обозначенный «шестерней».

	3.	Произвести изменение настроек (например, изменить язык приложения с русского на английский).

	4.	Закрыть приложение.

	5.	Открыть приложение снова.

Ожидаемый результат

После перезапуска приложение должно отобразиться с учетом настроек, указанных ранее.

Проверка всех тест-кейсов осуществляется за счет операции сравнения, которая производится с «пустыми» полями. В случае отсутствия каких-либо параметров используются либо стандартные значения, либо вывод соответствующей ошибки. 
 
Проверка метода «equals» производится предоставленным ниже тест-кейсом.



***Мокапы к приложению «trainingTime»***
***mockups for «trainingTime»***

![alt text](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/1.jpg?raw=true)


![alt text](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/2.jpg?raw=true)


![alt text](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/3.jpg?raw=true)

***UML-диаграмма к приложению «trainingTime»***
***UML for «trainingTime»***

![alt text](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/uml.jpg?raw=true)


***Подробная UML-диаграмма к приложению trainingTime***
***UML for «trainingTime» (parted)***
![UML](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/uml1.jpg?raw=true)
![UML](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/uml2.jpg?raw=true)
![UML](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/uml3.jpg?raw=true)

***Диаграмма развертываемости к приложению trainingTime***
![UML](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/diagramUML.png?raw=true)


**Скриншоты из приложения с пояснениями к ним**


***Темная тема в приложенияя***
![work](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/work1.jpg?raw=true)

***Светлая тема в приложении***
![work](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/work2.jpg?raw=true)

***Процесс создания индивидуальной тренировки***
![work](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/work3.jpg?raw=true)

***Экран перед началом выбранной тренировки***
![work](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/work4.jpg?raw=true)

***Процесс выполнения тренировки***
![work](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/work5.jpg?raw=true)

***Завершение тренироки***
![work](https://github.com/albaSANDROS/trainingTime/blob/master/pictures/work6.jpg?raw=true)


