import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;


public class MemberArchive {
    /**
     * Sølvmedlemsgrensen.
     */
    static final int SILVER_LIMIT = 25000;
    /**
     * Gullmedlemsgrensen.
     */
    static final int GOLD_LIMIT = 75000;
    /**
     * Tilfeldig tall.
     */
    static final Random RANDOM_NUMBER = new Random();
    /**
     * Medlemmene.
     */
    ArrayList<BonusMember> members;

    /**
     * Oppretter et nytt medlemsarkiv
     * @param members medlemmene
     */
    public MemberArchive(ArrayList<BonusMember> members) {
        this.members = members;
    }

    /**
     * Oppretter et nytt medlemsarkiv.
     */
    public MemberArchive(){
        this.members = new ArrayList<>();
    }

    /**
     * Metoden	oppretter	et	objekt	av	klassen	BasicMember og	legger	dette	inn	i	arkivet.
     * (Alle	medlemmer	begynner	som	basic-medlemmer.)	Metoden	returnerer
     * medlemsnummeret som	tildeles medlemmet.
     *
     * @param pers person informasjon
     * @param date datoen
     * @return the medlemsnummer
     */
    public int addMember(Personals pers, LocalDate date) {
        int memberNo = findAvailableNo();
        members.add(new BasicMember(memberNo, pers, date));
        return memberNo;
    }

    /**
     * Tar medlemsnummer og passord som argument og returnere antall poeng
     * denne kunden har spart opp. Returner en negativ verdi hvis medlem med dette nummeret
     * ikke fins, eller passord er ugyldig.
     *
     * @param memberNo medlemsnummeret
     * @param password passordet
     * @return poengene
     */
    public int findPoints(int memberNo, String password) {
        BonusMember member = findMember(memberNo);
        if (member != null && member.okPassword(password)) {
            return member.getPoints();
        }
        return -1;
    }

    /**
     * Tar medlemsnummer og antall poeng som argument og sørger for at
     * riktig antall poeng blir registrert for dette medlemmet. Returner false dersom medlem med
     * dette nummeret ikke fins.
     *
     * @param memberNo medlemsnummeret
     * @param points   antall poeng
     * @return boolean
     */
    public boolean registerPoints(int memberNo, int points) {
        BonusMember member = findMember(memberNo);
        if (member != null) {
            member.registerPoints(points);
            return true;
        }
        return false;
    }

    /**
     * Går gjennom alle medlemmene og foreta oppgradering av
     * medlemmer som er kvalifisert for det. Basic-medlemmer kan kvalifisere seg for sølv eller
     * gull, mens sølvmedlemmer kan kvalifisere seg for gull.
     *
     * @param date datoen
     */
    public void checkMembers(LocalDate date) {
        for (int i = 0; i < members.size(); i++) {
            BonusMember member = members.get(i);
            int qualifyingPoints = member.findQualificationPoints(date);
            if (qualifyingPoints >= GOLD_LIMIT && !(member instanceof GoldMember)) {
                members.set(i,
                        new GoldMember(
                                member.getMemberNo(),
                                member.getPersonals(),
                                member.getEnrolledDate(),
                                member.getPoints()
                        )
                );
            } else if (qualifyingPoints >= SILVER_LIMIT && !(member instanceof SilverMember)) {
                members.set(i,
                        new SilverMember(
                                member.getMemberNo(),
                                member.getPersonals(),
                                member.getEnrolledDate(),
                                member.getPoints()
                        )
                );
            }
        }
    }

    /**
     * Tar et medlemsnummber som parameter og sjekker om det fins ett medlem i members som har likt nummer
     * Hvis det fins returnerer den denne.
     * @param memberNo medlemnummeret
     */

    private BonusMember findMember(int memberNo) {
        for (BonusMember member : members) {
            if (member.getMemberNo() == memberNo) {
                return member;
            }
        }
        return null;
    }

    /**
     * Bruker findMember metoden for å se om det finnes et medlem med dette medlemsnummeret, hvis det ikke gjør det retunerer den dette tallet
     *
     * @return  memberNo medlemsnummeret
     */

    private int findAvailableNo() {
        while (true) {
            int memberNo = RANDOM_NUMBER.nextInt();
            if (findMember(memberNo) == null) {
                return memberNo;
            }
        }
    }
}