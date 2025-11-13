package studio.rossxrio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppActions {

    public static class CloseFrame extends AbstractAction{
        private final JFrame context;

        public CloseFrame(JFrame context) {
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            context.dispose();
        }
    }

    public static class CreateNewNote extends AbstractAction{
        private final JPanel context;

        public CreateNewNote(JPanel context) {
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            context.add(new Note(new Data("new")));
            context.setLayout(new GridLayout(Math.max(10, context.getComponents().length), 1, 10, 10));
            context.getParent().revalidate();
            context.getParent().repaint();
        }
    }

    public static class DeleteLastNote extends AbstractAction{
        private final JPanel context;

        public DeleteLastNote(JPanel context) {
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            context.remove(context.getComponent(context.getComponents().length - 1));
            context.getParent().revalidate();
            context.getParent().repaint();
        }
    }

    public static class OpenLastNote extends AbstractAction{
        private final JPanel context;

        public OpenLastNote(JPanel context) {
            this.context = context;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Note note = (Note)context.getComponent(context.getComponents().length - 1);
            note.openStickyNote();
        }
    }
}
