import Shared
import SwiftUI

struct SearchView: View {
    @InjectedStateObject private var viewModel: SearchViewModel

    var body: some View {
        _SearchView(
            query: $viewModel.query,
            searchResultUiModel: viewModel.searchResultUiModel
        ) { newQuery in
            if newQuery {
                viewModel.searchWithNewQuery()
            } else {
                viewModel.search()
            }
        }
        .onReceive(viewModel.$searchResultUiModel) { uiModel in
            if let error = uiModel.error {
                // TODO: Show error message.
                Komol.e(error)
            }
        }
        .navigationBarTitle(Text("GitHub Browser"), displayMode: .inline)
    }
}

private struct _SearchView: View {
    private let query: Binding<String>

    private let searchResultUiModel: SearchResultUiModel

    private let search: (Bool) -> Void

    init(query: Binding<String>,
         searchResultUiModel: SearchResultUiModel,
         search: @escaping (Bool) -> Void = { _ in })
    {
        self.query = query
        self.searchResultUiModel = searchResultUiModel
        self.search = search
    }

    var body: some View {
        VStack(spacing: 8) {
            SearchHeaderView(
                query: query,
                disabled: searchResultUiModel.inProgress
            ) {
                search(true)
            }
            .padding()

            if searchResultUiModel.firstInProgress {
                InProgressView()
            }

            if let searchResult = searchResultUiModel.searchResult {
                RepoListView(
                    repos: searchResult.repos,
                    showsFooterProress: searchResultUiModel.pagingInProgress
                ) {
                    search(false)
                }
            }

            if let error = searchResultUiModel.error,
               searchResultUiModel.searchResult == nil
            {
                RetryView(message: error) {
                    search(true)
                }
            }

            if searchResultUiModel.hasNoState {
                EmptyView()
            }
        }
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        let searchResultUiModel = SearchResultUiModel(
            searchResult: .init(repos: [
                Repo(
                    id: 1,
                    name: "kmm-github-browser-sample",
                    fullName: "droibit/kmm-github-browser-sample",
                    description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                    stars: 0,
                    ownerLogin: "droibit",
                    ownerUrl: ""
                ),
            ],
            nextPage: nil)
        )

        Group {
            _SearchView(
                query: .constant("kmm"),
                searchResultUiModel: searchResultUiModel
            )
            .previewDevice("iPhone SE (2nd generation)")
            .preferredColorScheme(.light)

            _SearchView(
                query: .constant("kmm"),
                searchResultUiModel: searchResultUiModel
            )
            .previewDevice("iPhone 12")
            .preferredColorScheme(.dark)
        }
    }
}
