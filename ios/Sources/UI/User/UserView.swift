import Nuke
import NukeUI
import Shared
import SwiftUI

struct UserView: View {
    let login: String

    var body: some View {
        VStack(alignment: .leading) {
            makeHeaderView(
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

    private func makeHeaderView(user: User) -> some View {
        HStack(spacing: 16) {
            let iconSize: CGFloat = 64
            LazyImage(source: user.avatarUrl) { state in
                if let image = state.image {
                    image
                } else {
                    SwiftUI.Image(systemName: "person.crop.circle.fill")
                        .resizable()
                        .frame(width: 48, height: 48)
                        .scaledToFit()
                }
            }
            .processors([ImageProcessors.Resize(width: iconSize)])
            .clipShape(Circle())
            .overlay(Circle().stroke(Color.secondary.opacity(0.25), lineWidth: 1))
            .frame(width: iconSize, height: iconSize)

            VStack(alignment: .leading, spacing: 8) {
                Text(user.name!)
                    .font(.title3.bold())
                Text(user.login)
                    .font(.body)
                    .foregroundColor(.secondary)
            }
        }
        .padding()
    }
}

struct UserView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            UserView(login: "droibit")
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(Color(UIColor.systemBackground))
                .preferredColorScheme(.light)

            UserView(login: "droibit")
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(Color(UIColor.systemBackground))
                .previewDevice("iPhone 12")
                .preferredColorScheme(.dark)
        }
    }
}
