import Combine
import Nuke
import NukeUI
import Shared
import SwiftUI

struct UserView: View {
    @InjectedStateObject var viewModel: UserViewModel

    let login: String

    var body: some View {
        _UserView(
            getUserUiModel: viewModel.getUserUiModel
        ) {
            viewModel.getUser(login: login)
        }
        .onAppear {
            viewModel.onAppear(login: login)
        }
        .onReceive(viewModel.$getUserUiModel) { uiModel in
            if let error = uiModel.error {
                // TODO: Show error message.
                Komol.e(error)
            }
        }
        .navigationBarTitle("User", displayMode: .inline)
    }
}

private struct _UserView: View {
    private let getUserUiModel: GetUserUiModel

    private let refresh: () -> Void

    init(getUserUiModel: GetUserUiModel,
         refresh: @escaping () -> Void = {})
    {
        self.getUserUiModel = getUserUiModel
        self.refresh = refresh
    }

    var body: some View {
        Group {
            if getUserUiModel.inProgress {
                InProgressView()
            }

            if let uiModel = getUserUiModel.userUiModel {
                makeContentView(with: uiModel)
            }

            if let error = getUserUiModel.error {
                RetryView(message: error, retryAction: refresh)
            }

            if getUserUiModel.hasNoState {
                InProgressView()
            }
        }
        .transition(.fade)
    }

    private func makeContentView(with uiModel: UserUiModel) -> some View {
        VStack(alignment: .leading) {
            UserHeaderView(user: uiModel.user)
            RepoListView(
                header: "Repositories",
                repos: uiModel.repos,
                showsFooterProress: false
            )
        }
    }
}

struct UserView_Previews: PreviewProvider {
    static var previews: some View {
        let getUserUiModel = GetUserUiModel(
            userUiModel: .init(
                user: User(
                    login: "droibit",
                    avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4",
                    name: "Shinya Kumagai",
                    company: nil,
                    reposUrl: nil,
                    blog: nil
                ),
                repos: [
                    Repo(
                        id: 1,
                        name: "kmm-github-browser-sample",
                        fullName: "droibit/kmm-github-browser-sample",
                        description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                        stars: 0,
                        ownerLogin: "droibit",
                        ownerUrl: ""
                    ),
                ]
            )
        )

        Group {
            _UserView(getUserUiModel: getUserUiModel)
                .previewDevice("iPhone SE (2nd generation)")
                .preferredColorScheme(.light)

            _UserView(getUserUiModel: getUserUiModel)
                .previewDevice("iPhone 12")
                .preferredColorScheme(.dark)
        }
    }
}
