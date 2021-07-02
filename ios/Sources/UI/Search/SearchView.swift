import Shared
import SwiftUI

struct SearchView: View {
    var body: some View {
        VStack(spacing: 8) {
            let searchResultUiModel = SearchResultUiModel(inProgress: true)

            SearchHeaderView(query: .constant(""), disabled: searchResultUiModel.inProgress) {}
                .padding()

            if searchResultUiModel.firstInProgress {
                InProgressView()
            }

            if let searchResult = searchResultUiModel.searchResult {
                RepoListView(
                    repos: searchResult.repos,
                    showsFooterProress: searchResultUiModel.pagingInProgress
                ) {}
            }
        }
        .navigationBarTitle(Text("GitHub Browser"), displayMode: .inline)
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            SearchView()
                .background(Color(UIColor.systemBackground))
                .previewDevice("iPhone SE (2nd generation)")
                .preferredColorScheme(.light)

            SearchView()
                .background(Color(UIColor.systemBackground))
                .previewDevice("iPhone 12")
                .preferredColorScheme(.dark)
        }
    }
}
