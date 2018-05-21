﻿# Graphs

Построение графа дорог города Владивосток.

Для запуска проекта нужны библиотеки jdom (для работы с xml файлами) и opencsv(для работы с csv файлами).

В папке "result" находится файл graph.svg - визуализация полученного графа, файл vertices.csv - содержащий 
id вершины и её декартовы координаты и файл edges.csv - содержит пары вида (id1,id2), где id1 - id вершины 
из которой исходит ребро графа, id2 - id вершины, куда приходит это ребро. 




Для задания 2, нужно запустить программу и в меню пользователя выбрать 1 (если вы хотите увидеть путь до ближайшего ресторана),
затем ввести текущие координаты пользователя. 

По умолчанию, кратчайшие пути считаются алгоритмом A*, если вы хотите посчитать другим алгоритмом, то нужно раскомментировать
соответствующие выбранному методу строки в программе. 

После выполнения программы в папке result появляется файл "ways.csv" с путями от точки, где находится человек, до каждого ресторана. 
В "graph.svg"  отрисуются все дороги, кратчайшая будет выделена  красным.





Для задания 3, нужно запустить программу и в меню пользователя выбрать 2 (если вы хотитие увидеть решение задачи коммивояжера),
затем ввести текущие координаты пользователя. 

По умолчанию, задача коммивояжера решается методом ближайшего соседа,если вы хотите посчитать другим алгоритмом, то нужно раскомментировать соответствующие выбранному методу строки в программе. 

После выполнения программы в папке result появляется файл "ways.csv" с путями от точки, где находится человек, с обходом каждого ресторана и с возвращением в исходную точку. 

В "graph.svg"  отрисуются все дороги, последний путь (возврат в исходную точку) будет выделен  красным.
