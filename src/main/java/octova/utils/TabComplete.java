package octova.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete {
	public static List<String> filter(List<String> options, String[] args) {
        if (args.length == 0) return options;
        
        int doneTypedLength = 0;
        for (int i = 0; i < args.length - 1; ++i) doneTypedLength += args[i].length() + 1;
        
        String typed = String.join(" ", args);
        
        List<String> completions = new ArrayList<>();
        
		for (String s : options) {
            if (s.startsWith(typed)) {
                completions.add(s.substring(doneTypedLength).trim());
            }
        }
        
        Collections.sort(completions);
        if (completions.size() == 0) return null;
        return completions;
	}
}
