import Shared
import SwiftUI

struct ContributorListView: View {
    let contributors: [Contributor]

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("Contributors")
                .font(.headline.weight(.regular))
                .padding(.horizontal)

            if contributors.isEmpty {
                EmptyView()
            } else {
                List {
                    ForEach(contributors) { contributor in
                        NavigationLink(destination: UserView(login: contributor.login)) {
                            ContributorItemView(contributor: contributor)
                        }
                    }
                }
                .listStyle(PlainListStyle())
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct ContributorListView_Previews: PreviewProvider {
    static var previews: some View {
        let contributors = [
            Contributor(
                repoName: "kmm-github-browser-sample",
                repoOwner: "droibit",
                login: "droibit",
                contributions: 47,
                avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4"
            ),
        ]

        Group {
            ContributorListView(contributors: contributors)
                .preferredColorScheme(.light)

            ContributorListView(contributors: contributors)
                .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
