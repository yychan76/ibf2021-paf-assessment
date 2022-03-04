package ibf.paf.portfolio.models;

import java.util.List;

public record QuerySymbolsResult(
                List<Symbol> matches,
                Integer count) {
        public QuerySymbolsResult(List<Symbol> matches) {
                this(matches, matches.size());
        }

}
