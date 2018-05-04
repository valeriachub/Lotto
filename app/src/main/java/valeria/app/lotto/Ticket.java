package valeria.app.lotto;

/**
 * Created by valeria on 04.05.2018.
 */

public class Ticket {
    private int number;
    private boolean isSelected;

    public Ticket(int number, boolean isSelected) {
        this.number = number;
        this.isSelected = isSelected;
    }

    public int getNumber() {
        return number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
