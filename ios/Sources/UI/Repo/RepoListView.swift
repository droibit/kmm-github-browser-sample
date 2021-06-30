import Shared
import SwiftUI

struct RepoListView: View {
    let repos: [Repo]

    var body: some View {
        List {
            ForEach(repos) { repo in
                RepoItemView(repo: repo)
            }
        }
        .listStyle(PlainListStyle())
    }
}

struct RepoListView_Previews: PreviewProvider {
    static var previews: some View {
        let repos = [
            Repo(
                id: 1,
                name: "kmm-github-browser-sample",
                fullName: "droibit/kmm-github-browser-sample",
                description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                stars: 0,
                ownerLogin: "",
                ownerUrl: ""
            ),
            Repo(
                id: 2,
                name: "hell-kmm",
                fullName: "droibit/hell-kmm",
                description: "Hello world app of Kotlin multiplatform mobile.",
                stars: 999,
                ownerLogin: "",
                ownerUrl: ""
            ),
            Repo(
                id: 3,
                name: "architecture-components-samples",
                fullName: "android/architecture-components-samples",
                description: "Samples for Android Architecture Components.",
                stars: 20400,
                ownerLogin: "",
                ownerUrl: ""
            ),
            Repo(
                id: 4,
                name: "architecture-components-samples",
                fullName: "android/architecture-components-samples",
                description: "Samples for Android Architecture Components.",
                stars: 20400,
                ownerLogin: "",
                ownerUrl: ""
            ),
        ]
        

        RepoListView(repos: repos)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemBackground))
            .environment(\.colorScheme, .light)

        RepoListView(repos: repos)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemBackground))
            .previewDevice("iPhone 12")
            .environment(\.colorScheme, .dark)
    }
}
