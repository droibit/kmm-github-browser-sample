import Shared
import SwiftUI

struct RepoItemView: View {
    let repo: Repo

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                Image(systemName: "star.fill")
                    .foregroundColor(.yellow)
                Text("\(repo.stars)")
                    .foregroundColor(.yellow)

                Text(repo.fullName)
                    .padding(.leading, 4)
            }
            .layoutPriority(1)
            .font(.title3)

            if let desc = repo.description_ {
                Text(desc)
                    .foregroundColor(.gray)
                    .font(.body)
            }
        }
        .padding(.horizontal)
        .padding(.vertical, 8)
        .frame(minHeight: 48)
    }
}

struct RepoItemView_Previews: PreviewProvider {
    static var previews: some View {
        RepoItemView(
            repo: Repo(
                id: 1,
                name: "kmm-github-browser-sample",
                fullName: "droibit/kmm-github-browser-sample",
                description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                stars: 0,
                ownerLogin: "",
                ownerUrl: ""
            )
        )
        .background(Color(UIColor.systemBackground))
        .previewLayout(.sizeThatFits)
        .environment(\.colorScheme, .light)

        RepoItemView(
            repo: Repo(
                id: 1,
                name: "hell-kmm",
                fullName: "droibit/hell-kmm",
                description: "Hello world app of Kotlin multiplatform mobile.",
                stars: 999,
                ownerLogin: "",
                ownerUrl: ""
            )
        )
        .background(Color(UIColor.systemBackground))
        .previewLayout(.sizeThatFits)
        .environment(\.colorScheme, .dark)
    }
}
