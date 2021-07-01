import Shared
import SwiftUI

struct ContributorListView: View {
    let contributors: [Contributor]

    var body: some View {
        if contributors.isEmpty {
            EmptyView()
        } else {
            VStack(alignment: .leading) {
                Text("Contributors")
                    .font(.headline)
                    .padding(.horizontal)
                List(contributors) { contributor in
                    NavigationLink(destination: UserView(login: contributor.login)) {
                        ContributorItemView(contributor: contributor)
                    }
                }
                .listStyle(PlainListStyle())
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
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

        ContributorListView(contributors: contributors)
            .background(Color(UIColor.systemBackground))
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .light)

        ContributorListView(contributors: contributors)
            .background(Color(UIColor.systemBackground))
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .dark)
    }
}
