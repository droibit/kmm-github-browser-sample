import Shared
import SwiftUI

struct RepoView: View {
    let repo: Repo

    var body: some View {
        VStack(alignment: .leading) {
            VStack(alignment: .leading, spacing: 8) {
                Text(repo.fullName)
                    .font(.title2.bold())

                if let desc = repo.description_, !desc.isEmpty {
                    Text(desc)
                        .font(.body)
                        .foregroundColor(.secondary)
                }
            }
            .padding()

            ContributorListView(contributors: [
                Contributor(
                    repoName: "kmm-github-browser-sample",
                    repoOwner: "droibit",
                    login: "droibit",
                    contributions: 47,
                    avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4"
                ),
            ])
        }
        .navigationBarTitle(Text("Repository"), displayMode: .inline)
    }
}

struct RepoView_Previews: PreviewProvider {
    static var previews: some View {
        RepoView(
            repo: Repo(
                id: 1,
                name: "kmm-github-browser-sample",
                fullName: "droibit/kmm-github-browser-sample",
                description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                stars: 0,
                ownerLogin: "droibit",
                ownerUrl: ""
            )
        )
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(UIColor.systemBackground))
        .previewDevice("iPhone SE (2nd generation)")
        .environment(\.colorScheme, .light)

        RepoView(
            repo: Repo(
                id: 1,
                name: "kmm-github-browser-sample",
                fullName: "droibit/kmm-github-browser-sample",
                description: nil,
                stars: 0,
                ownerLogin: "droibit",
                ownerUrl: ""
            )
        )
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(UIColor.systemBackground))
        .previewDevice("iPhone 12")
        .environment(\.colorScheme, .dark)
    }
}
