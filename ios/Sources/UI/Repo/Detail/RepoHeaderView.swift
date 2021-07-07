import Shared
import SwiftUI

struct RepoHeaderView: View {
    let repo: Repo

    var body: some View {
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
    }
}

struct RepoHeaderView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            let repo = Repo(
                id: 1,
                name: "kmm-github-browser-sample",
                fullName: "droibit/kmm-github-browser-sample",
                description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                stars: 0,
                ownerLogin: "droibit",
                ownerUrl: ""
            )

            RepoHeaderView(repo: repo)
                .preferredColorScheme(.light)

            RepoHeaderView(repo: repo)
                .preferredColorScheme(.dark)
        }
        .frame(width: 320)
        .previewLayout(.sizeThatFits)
    }
}
