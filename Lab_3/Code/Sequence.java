// класс ПОСЛЕДОВАТЕЛЬНОСТЬ, через него мы создаём последовательность, заполняем её доминошками в случайном порядке,
// выводим её, можем получить индекс её последнего эллемента или поменять его, можем добавить элемент в конец последовательности,
// а также удалить элемент, на который указывает ссылка из текущего элемента
public class Sequence {
    private int finish; // переменная содержит индекс последнего элемента последовательности
    private Domino[] Base; // переменная содержит ссылку на упорядоченный массив доминошек
    // конструктор последовательности
    public Sequence (Domino[] A){ // передаём в конструктор ссылку на упорядоченный массив доминошек
        finish = -1; // пока последовательность пустая, finish равен -1
        Base = A;
    }
    // метод перезаписывает индекс последнего элемента на новый индекс
    public void changeFinish(int index){ // метод получает индекс, который необходимо вписать в finish
        finish = index;
    }
    // метод возвращает индекс элемента, являющегося стартовой позицией
    public int getStartPosition(){
        return finish;
    }
    // метод добавляет элемент в конец последовательности
    public void addElement(Domino A){ // получает элемент, который станет концом последовательности
        int index = A.getNumber();
        if (finish != -1) { // если последовательность не пустая
            int copy = (Base[finish].getLink() == -1 ? finish : Base[finish].getLink()); // если элемент добавляется первым, то ссылается сам на себя
            Base[finish].addLink(index); // записывает текущему последнему элементу ссылку на новый последний элемент
            Base[index].addLink(copy); // записываем в новый последний элемент ссылку на первый элемент
            finish = index; // заменяем finish на индекс нового последнего элемента
        }
        else { // если пустая, то финишем станет номер добавляемого элемента и он сошлётся сам на себя
            finish = A.getNumber();
            A.addLink(finish);
        }
    }
    // метод удаляет элемент через step шагов после элемента, позицию которого мы передали
    public Domino delete(int position, int step){
        // получает position, от которого надо шагать и оптимизированное кол-во шагов
        position = StepPosition(position, step); // отдельный приватный метод делает шаги и отдаёт новый position
        if (Base[position].getLink() == finish){
            // если случилось так, что нам нужно удалить последний элемент в последовательности, то мы
            // проверяем, остались ли вообще какие-то элементы в последовательности
            if (position == finish)
                finish = -1; // если это не так, то finish = -1
            else
                finish = position; // если это так, то finish заменяется на элемент, стоящий перед ним
        }
        Domino fordelete = Base[Base[position].getLink()];
        Base[position].addLink(fordelete.getLink());
        // в элемент с индексом position мы перезаписываем ссылку на элемент, на который ссылался удаляемый элемент
        // таким образом мы больше никогда не сможем попасть в удаляемый элемент и всегда будем идти дальше
        fordelete.addLink(position); // в ссылку удаляемого элемента записываем position, чтобы удобно вытащить всё вместе
        // position действительно хранит предыдущий
        return fordelete; // возвращаем удалённый элемент
    }
    // вспомогательный метод для изменения position
    private int StepPosition(int position, int step){
        for (int i = 0; i < step; i++) position = Base[position].getLink();
        // мы работаем не с массивом, поэтому не может делать шаги сразу на несколько элементов вперёд,
        // поэтому будем делать step шагов, перед тем, как удалить новый элемент.
        return position;
    }
    // метод выводит всю последовательность доминошек
    public void print(){
        if (finish != -1) { // проверяем, не пуста ли последовательность по значению её конца
            int printIndex = Base[finish].getLink(); // переменная отвечает за индекс выводимого элемента
            // так как мы движемся не по индексам из массива, а по индексам из последовательности, то первым
            // на вывод будет тот, на кого ссылается последняя доминошка
            while (printIndex != finish){ // выводим, пока следующим на выведение не окажется последним
                Base[printIndex].print(); // выводим элемент
                printIndex = Base[printIndex].getLink(); // переставляем индекс на следующий элемент
            }
            Base[printIndex].print(); // выводим последний элемент
            System.out.println();
        }
    }
}
