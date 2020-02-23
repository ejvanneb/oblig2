import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BonusMember {

    final float FACTOR_SILVER = 1.2F;

    final float FACTOR_GOLD = 1.5F;

    private final int memberNo;
    private final Personals personals;
    private final LocalDate enrolledDate;
    private int bonusPoints = 0;

    BonusMember(int memberNo, Personals personals, LocalDate enrolledDate, int points) {
        this.memberNo = memberNo;
        this.personals = personals;
        this.enrolledDate = enrolledDate;
        this.bonusPoints = points;
    }

    BonusMember(int memberNo, Personals personals, LocalDate enrolledDate) {
        this.memberNo = memberNo;
        this.personals = personals;
        this.enrolledDate = enrolledDate;
        this.bonusPoints = 0;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public Personals getPersonals() {
        return personals;
    }

    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }

    public int getPoints() {
        return bonusPoints;
    }

    public int findQualificationPoints(LocalDate date) {
        if(ChronoUnit.DAYS.between(this.enrolledDate, date) < 365){
            return getPoints();
        }
        return 0;
    }

    public boolean okPassword(String password) {
        return getPersonals().okPassword(password);
    }

    public void registerPoints(int points){
        this.bonusPoints += points;
    }
}