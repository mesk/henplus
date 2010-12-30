package henplus.jline;

import java.util.List;

import jline.Completor;

public class ReadlineCompleterAdapter implements Completor {

    private final String wordBreakCharacters;

    private final ReadlineCompleter readLineCompleter;

    public ReadlineCompleterAdapter(final String wordBreakCharacters, final ReadlineCompleter readlineCompleter) {
        this.wordBreakCharacters = wordBreakCharacters;
        this.readLineCompleter = readlineCompleter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int complete(final String buffer, final int cursor, final List candidates) {

        // need to find the last incomplete word
        int lastBreak = buffer.length() - 1;
        while ((lastBreak > 0) && (wordBreakCharacters.indexOf(buffer.charAt(lastBreak)) == -1)) {
            lastBreak--;
        }

        final String lastWord = buffer.substring(lastBreak, buffer.length());

        String nextCompletion = null;
        int state = 0;
        while ((nextCompletion = readLineCompleter.completer(lastWord, state)) != null) {
            candidates.add(nextCompletion);
            state++;
        }

        return lastBreak;
    }
}