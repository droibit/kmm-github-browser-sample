import Nuke
import NukeUI
import Shared
import SwiftUI

struct UserView: View {
    let login: String

    var body: some View {
        VStack(alignment: .leading) {
            UserHeaderView(
                user: User(
                    login: "droibit",
                    avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4",
                    name: "Shinya Kumagai",
                    company: nil,
                    reposUrl: nil,
                    blog: nil
                )
            )

            RepoListView(
                header: "Repositories",
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
        }
        .navigationBarTitle("User")
    }
}

struct UserView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            UserView(login: "droibit")
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .preferredColorScheme(.light)

            UserView(login: "droibit")
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .previewDevice("iPhone 12")
                .preferredColorScheme(.dark)
        }
    }
}
