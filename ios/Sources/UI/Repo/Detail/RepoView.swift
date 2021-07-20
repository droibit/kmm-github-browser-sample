import Shared
import SwiftUI

struct RepoView: View {
    @InjectedStateObject var viewModel: RepoViewModel

    let owner: String

    let name: String

    var body: some View {
        _RepoView(
            getRepoUiModel: viewModel.getRepoUiModel
        ) {
            viewModel.getRepo(owner: owner, name: name)
        }
        .onAppear {
            viewModel.onAppear(owner: owner, name: name)
        }
        .onReceive(viewModel.$getRepoUiModel) { uiModel in
            if let error = uiModel.error {
                // TODO: Show error message.
                Komol.e(error)
            }
        }
        .navigationBarTitle(Text("Repository"), displayMode: .inline)
    }
}

private struct _RepoView: View {
    private let getRepoUiModel: GetRepoUiModel

    private let refresh: () -> Void

    init(getRepoUiModel: GetRepoUiModel,
         refresh: @escaping () -> Void = {})
    {
        self.getRepoUiModel = getRepoUiModel
        self.refresh = refresh
    }

    var body: some View {
        Group {
            if getRepoUiModel.inProgress {
                InProgressView()
            }

            if let uiModel = getRepoUiModel.repoUiModel {
                makeContentView(with: uiModel)
            }

            if let error = getRepoUiModel.error {
                RetryView(message: error, retryAction: refresh)
            }

            if getRepoUiModel.hasNoState {
                InProgressView()
            }
        }
        .transition(.fade)
    }

    private func makeContentView(with uiModel: RepoUiModel) -> some View {
        VStack(alignment: .leading) {
            RepoHeaderView(repo: uiModel.repo)
            ContributorListView(contributors: uiModel.contributors)
        }
    }
}

struct RepoView_Previews: PreviewProvider {
    static var previews: some View {
        let getRepoUiModel = GetRepoUiModel(
            repoUiModel: .init(
                repo: Repo(
                    id: 1,
                    name: "kmm-github-browser-sample",
                    fullName: "droibit/kmm-github-browser-sample",
                    description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                    stars: 0,
                    ownerLogin: "droibit",
                    ownerUrl: ""
                ),
                contributors: [
                    Contributor(
                        repoName: "kmm-github-browser-sample",
                        repoOwner: "droibit",
                        login: "droibit",
                        contributions: 47,
                        avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4"
                    ),
                ]
            )
        )

        Group {
            _RepoView(getRepoUiModel: getRepoUiModel)
                .previewDevice("iPhone SE (2nd generation)")
                .preferredColorScheme(.light)

            _RepoView(getRepoUiModel: getRepoUiModel)
                .previewDevice("iPhone 12")
                .preferredColorScheme(.dark)
        }
    }
}
