public class MethodsRunner extends Thread {

    private ValueChanger valueChanger;
    private String name;

    MethodsRunner(ValueChanger valueChanger, String name) {
        this.valueChanger = valueChanger;
        this.name = name;
        start();
    }

    @Override
    public void run() {
        valueChanger.printNumbers(name);
        valueChanger.printNumbersSync(name);
    }
}