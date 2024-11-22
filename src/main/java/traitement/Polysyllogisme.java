package traitement;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Polysyllogisme implements Validateur {



    protected Proposition conclusion;
    protected List<Proposition> premises = new ArrayList<>();
    private List<String> invalid = new ArrayList<>();
    private int taillePremisses =0 ;


    public Proposition getConclusion() {
        return conclusion;
    }

    public List<Proposition> getPremises() {
        return premises;
    }

    public void setConclusion(Proposition conclusion) {
        this.conclusion = conclusion;
        this.taillePremisses = this.premises.size();
    }

    public void setPremises(List<Proposition> premises) {
        this.premises = premises;
    }


    public boolean termesEgaux(String termeA, String termeB) {
        return termeA.equals(termeB);
    }

    public boolean deuxPropsValide(String premierTermeTmp, String deuxiemeTermeTmp, String premierTerme, String deuxiemeTerme) {
        boolean communA = termesEgaux(deuxiemeTermeTmp, premierTerme) || termesEgaux(deuxiemeTermeTmp, deuxiemeTerme);
        boolean communB = termesEgaux(premierTermeTmp, premierTerme) || termesEgaux(premierTermeTmp, deuxiemeTerme);

        return (communA != communB) && (!termesEgaux(deuxiemeTermeTmp, premierTermeTmp) && !termesEgaux(premierTerme, deuxiemeTerme));
    }


    public Terme getIsolatedTerm(Proposition p1, Proposition p2) {
        String un = p1.getPremierTerme().getExpression();
        String deux = p1.getDeuxiemeTerme().getExpression();
        String trois = p2.getPremierTerme().getExpression();
        String quatre = p2.getDeuxiemeTerme().getExpression();

        System.out.println(un + deux + trois + quatre);

        if (termesEgaux(un, trois) && !termesEgaux(deux, quatre)) {
            return p1.getDeuxiemeTerme();
        }
        if (termesEgaux(un, quatre) && !termesEgaux(deux, trois)) {
            return p1.getDeuxiemeTerme();
        }
        if (termesEgaux(deux, trois) && !termesEgaux(un, quatre)) {
            return p1.getPremierTerme();
        }

        if (termesEgaux(deux, quatre) && !termesEgaux(un, trois)) {
            return p1.getPremierTerme();
        }

        return null;
    }

    public Terme getFstIsolated() {
        if (premises.size() < 2) return null;
        return getIsolatedTerm(premises.get(0), premises.get(1));
    }

    public Terme getLstIsolated() {
        if (premises.size() < 2) return null;
        Proposition last = premises.removeLast();
        Proposition secondLast = premises.removeLast();

        premises.add(secondLast);
        premises.add(last);


        return getIsolatedTerm(last, secondLast);
    }



    public boolean conclusionRespected() {
        Terme predicat = getFstIsolated();
        Terme sujet = getLstIsolated();
        System.out.println(predicat);
        System.out.println(sujet);

        if (predicat == null || sujet == null) return false;

        String premierTerme = conclusion.getPremierTerme().getExpression();
        String deuxiemeTerme = conclusion.getDeuxiemeTerme().getExpression();

        System.out.println("104" + premierTerme + " " + deuxiemeTerme + " " + predicat + " " + sujet);

        boolean predicatEgalPremierTerme = termesEgaux(predicat.getExpression(), premierTerme);
        boolean sujetEgalDeuxiemeTerme = termesEgaux(sujet.getExpression(), deuxiemeTerme);
        boolean predicatEgalDeuxiemeTerme = termesEgaux(predicat.getExpression(), deuxiemeTerme);
        boolean sujetEgalPremierTerme = termesEgaux(sujet.getExpression(), premierTerme);


        return (predicatEgalPremierTerme && sujetEgalDeuxiemeTerme) ||
                (predicatEgalDeuxiemeTerme && sujetEgalPremierTerme);
    }


    public boolean structValid() {
        Proposition tmpProp = null;

        for (Proposition p : premises) {
            if (tmpProp != null) {
                String premierTermeTmp = tmpProp.getPremierTerme().getExpression();
                String deuxiemeTermeTmp = tmpProp.getDeuxiemeTerme().getExpression();
                String premierTerme = p.getPremierTerme().getExpression();
                String deuxiemeTerme = p.getDeuxiemeTerme().getExpression();

                if (!deuxPropsValide(premierTermeTmp, deuxiemeTermeTmp, premierTerme, deuxiemeTerme)) {
                    return false;
                }
            }
            tmpProp = p;
        }
        return conclusionRespected();
    }

    public Terme getPredicatConclusion()
    {
        return this.conclusion.getDeuxiemeTerme();
    }

    public Terme getSujetConclusion()
    {
        return this.conclusion.getPremierTerme();
    }

    public void swap(int indice , int indice2)
    {
        Proposition tmp = premises.get(indice);
        this.premises.set(indice , this.premises.get(indice2));
        this.premises.set(indice2 , tmp);

    }

    public boolean placeLast() {
        Terme sujet = getSujetConclusion();
        int indice = 0 ;
        for (Proposition p : premises) {
            if(termesEgaux(sujet.getExpression() , p.getPremierTerme().getExpression())
            || termesEgaux(sujet.getExpression() , p.getDeuxiemeTerme().getExpression()))
            {
                this.swap(this.taillePremisses -1  , indice);
                return true;
            }
            indice ++ ;

        }
        return true ;

    }

    public boolean placeProp(Proposition prop , int indice)
    {


        for (int i = 0; i < premises.size(); i++) {
            Proposition p = premises.get(i);
            String premierTermeTmp = prop.getPremierTerme().getExpression();
            String deuxiemeTermeTmp = prop.getDeuxiemeTerme().getExpression();
            String premierTerme = p.getPremierTerme().getExpression();
            String deuxiemeTerme = p.getDeuxiemeTerme().getExpression();
            if (deuxPropsValide(premierTermeTmp, deuxiemeTermeTmp, premierTerme, deuxiemeTerme)) {
                System.out.println(premierTerme+ deuxiemeTerme + premierTermeTmp + deuxiemeTermeTmp + i + indice);
                if(indice == 1)
                    return true ;
                this.swap(indice -1 , i );
                this.affichePremisse();
                boolean next = placeProp(p , indice -1);
                if(next)
                    return true ;
                this.affichePremisse();
            }
        }
        return  false ;

    }

    private void affichePremisse() {
    }

    public boolean structCorrection () {
          if(placeLast()) {
              return placeProp(this.premises.getLast(), this.taillePremisses - 1);
          }
          return  false;
    }

        /**
         * Method that allow the user to get a pair of medium term from two premises
         * */
        public Pair<Terme, Terme> getMediumTerm (Proposition p1, Proposition p2){
            Pair<Terme, Terme> mediumTerms;
            if (p1.getPremierTermeString().equals(p2.getPremierTermeString())) { //< cas ou le moyen terme est le premier terme dans les deux.
                mediumTerms = new Pair(p1.getPremierTerme(), p2.getPremierTerme());
                return mediumTerms;
            } else if (p1.getPremierTermeString().equals(p2.getDeuxiemeTermeString())) { //< cas ou le moyen terme est premier terme dans la premiere premise et deuxième dans la seconde.
                mediumTerms = new Pair(p1.getPremierTerme(), p2.getDeuxiemeTerme());
                return mediumTerms;
            } else if (p1.getDeuxiemeTermeString().equals(p2.getPremierTermeString())) {
                mediumTerms = new Pair<>(p1.getDeuxiemeTerme(), p2.getPremierTerme());
                return mediumTerms;
            } else if (p1.getDeuxiemeTermeString().equals(p2.getDeuxiemeTermeString())) {
                mediumTerms = new Pair<>(p1.getDeuxiemeTerme(), p2.getDeuxiemeTerme());
                return mediumTerms;
            } else {
                System.out.println("Pas de moyen terme : structure invalide");
                return null;
            }

        }

    //------------------------------------------------------------------//
    //--------------------------------RULES----------------------------//
    //----------------------------------------------------------------//


        // Cela risque d'être pas ouf si on doit parcourir la liste a chaque regle
        // TODO : essayer de faire en sorte qu'on parcourt une seul fois pour tester toutes les regles.


        @Override
        public void regleMoyenTerme() {
            for (int i = 0; i < premises.size() - 1; i++) {
                Pair<Terme, Terme> mediumTerms = getMediumTerm(premises.get(i), premises.get(i + 1));
                Terme firstMediumTerm = mediumTerms.getKey();
                Terme secondMediumTerm = mediumTerms.getValue();

                if (!firstMediumTerm.estUniverselle() && !secondMediumTerm.estUniverselle()) {
                    invalid.add("Medium Term");
                    return; //< Si on trouve que la règle est invalide on quitte la vérification.
                }
            }
        }




    @Override
    public void regleLatius() {


        for (Proposition p : premises) {

            if (p.getPremierTermeString().equals(conclusion.getPremierTermeString())) {
                if (p.getPremierTerme().estUniverselle() && !conclusion.getPremierTerme().estUniverselle()) {
                    invalid.add("Latius rule");
                    return;
                }
            }

            if (p.getPremierTermeString().equals(conclusion.getDeuxiemeTermeString())) {
                if (p.getPremierTerme().estUniverselle() && !conclusion.getDeuxiemeTerme().estUniverselle()) {
                    invalid.add("Latius rule");
                    return;
                }
            }

            if (p.getDeuxiemeTermeString().equals(conclusion.getPremierTermeString())) {
                if (p.getDeuxiemeTerme().estUniverselle() && !conclusion.getPremierTerme().estUniverselle()) {
                    invalid.add("Latius rule");
                    return;
                }
            }

            if (p.getDeuxiemeTermeString().equals(conclusion.getPremierTermeString())) {
                if (p.getPremierTerme().estUniverselle() && !conclusion.getDeuxiemeTerme().estUniverselle()) {
                    invalid.add("Latius rule");
                    return;
                }
            }

        }
    }




    @Override
    public void rNN() {
        for (int i = 0; i < premises.size() - 1; i++) {
            if (!premises.get(i).estAffirmative() && !premises.get(i + 1).estAffirmative() ) { //< Si les deux sont négatives la règle n'est pas réspecter
                invalid.add("rNN rule");
                return;
            }
        }
    }

    @Override
    public void rN() {
        for(Proposition p : premises) {
            if(!p.estAffirmative() && conclusion.estAffirmative()) {
                invalid.add("rN rule");
                return;
            }
        }
    }

    // Adnane
    @Override
    public void rAA() {
            int i = 0;
        for(Proposition a : premises) {
            if(a.estAffirmative()){
                i++;
            }
        }
        if(i == premises.size() && !conclusion.estAffirmative()){
            invalid.add("rAA");
        }
    }

    @Override
    public void rPP() {
        int i = 0;
        for(Proposition a : premises) {
            if(!a.estUniverselle()){
                i++;
            }
        }
        if(i==premises.size()) { //< Si toutes les prémises sont particulières.
            invalid.add("rPP");
        }
    }

    @Override
    public void rP() {
        int i = 0;
        for(Proposition a : premises) {
            if(!a.estUniverselle()){  //si l'une des prémisses est particuliers
                break;
            }
        }
        if(conclusion.estUniverselle()) { //< Et si la conclusion ne l'est pas.
                invalid.add("rP");
            }
        }

    @Override
    public void rUU() {
            int i = 0;
        for(Proposition a : premises) {
            if(a.estUniverselle()){
                i++;
            }
        }
        if(i==premises.size()) {
            if(!conclusion.estUniverselle()) {
                invalid.add("rUU");
            }
        }
    }
    // STOP
    @Override
    public Reponse valider() {
        return null;
    }

    Polysyllogisme() {
            this.invalid = new ArrayList<>();
    }

    public void addPremise(Quantificateur quantifier,String firstTerm, String secondTerm, boolean isAffirmatif) {
            Proposition premise = new Proposition(firstTerm,secondTerm,quantifier, isAffirmatif);
            premises.add(premise);

    }
    public void addConclusion(Quantificateur quantifier,String firstTerm, String secondTerm, boolean isAffirmatif) {
            Proposition conclusion = new Proposition(firstTerm,secondTerm,quantifier, isAffirmatif);
            this.conclusion = conclusion;
    }
}
