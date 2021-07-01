import Nuke
import NukeUI
import Shared
import SwiftUI

struct ContributorItemView: View {
    let contributor: Contributor

    var body: some View {
        HStack(spacing: 8) {
            let iconSize: CGFloat = 32
            LazyImage(source: contributor.avatarUrl) { state in
                if let image = state.image {
                    image
                } else {
                    SwiftUI.Image(systemName: "person.crop.circle.fill")
                        .resizable()
                        .frame(width: iconSize, height: iconSize)
                        .scaledToFit()
                }
            }
            .processors([ImageProcessors.Resize(width: iconSize)])
            .clipShape(Circle())
            .overlay(Circle().stroke(Color.secondary.opacity(0.25), lineWidth: 1))
            .frame(width: iconSize, height: iconSize)

            VStack(alignment: .leading, spacing: 4) {
                Text(contributor.login)
                    .font(.title3)
                Text("\(contributor.contributions) commits")
                    .font(.body)
                    .foregroundColor(.secondary)
            }
        }
        .padding(.vertical, 8)
        .frame(minHeight: 48)
    }
}

struct ContributorItemView_Previews: PreviewProvider {
    static var previews: some View {
        let contributor = Contributor(
            repoName: "kmm-github-browser-sample",
            repoOwner: "droibit",
            login: "droibit",
            contributions: 47,
            avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4"
        )

        ContributorItemView(contributor: contributor)
            .background(Color(UIColor.systemBackground))
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .light)

        ContributorItemView(contributor: contributor)
            .background(Color(UIColor.systemBackground))
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .dark)
    }
}
