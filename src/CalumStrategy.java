import java.util.ArrayList;
 
public class CalumStrategy implements Strategy {
 
        ArrayList<Dom> CalumDominoes = new ArrayList<Dom>();
        int leftEnd;
        int rightEnd;
 
        public CalumStrategy() {
 
        }
 
        public ArrayList<Dom> getCalumDominoes() {
                return CalumDominoes;
        }
 
        void setDoms() {
                GameState game = GameState.getInstance();
                CalumDominoes = game.getHand(game.getCurrentPlayer()).getDoms();
        }
 
        @Override
        public boolean playTile() {
                setDoms();
 
                GameState game = GameState.getInstance();
 
                leftEnd = game.getBoard().getLeftEnd();
                rightEnd = game.getBoard().getRightEnd();
 
                for (int i = 0; i < CalumDominoes.size(); i++) {
                        Dom dom = CalumDominoes.get(i);
                        if (dom.getLeft() == leftEnd || dom.getLeft() == rightEnd
                                        || dom.getRight() == leftEnd || dom.getRight() == rightEnd) {
                                game.getHand(game.getCurrentPlayer()).getDoms().remove(i);
                                if (dom.getLeft() == leftEnd || dom.getRight() == leftEnd) {
                                        if (dom.getRight() != leftEnd) {
                                                dom.flip();
                                        }
                                        game.getBoard().addDom(Loc.LEFT, dom); // LEFT, not left
                                } else {
                                        if (dom.getLeft() != rightEnd) {
                                                dom.flip();
                                        }
                                        game.getBoard().addDom(Loc.RIGHT, dom); // RIGHT, not right
                                }
                                return true;
                        }
                }
 
                return false;
        }
 
        @Override
        public String getName() {
                return "Calum";
        }
}