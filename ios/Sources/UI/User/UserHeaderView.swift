import Nuke
import NukeUI
import Shared
import SwiftUI

struct UserHeaderView: View {
    let user: User
    var body: some View {
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

struct UserHeaderView_Previews: PreviewProvider {
    static var previews: some View {
        let user = User(
            login: "droibit",
            avatarUrl: "https://avatars.githubusercontent.com/u/1456714?v=4",
            name: "Shinya Kumagai",
            company: nil,
            reposUrl: nil,
            blog: nil
        )
        Group {
            UserHeaderView(user: user)
                .preferredColorScheme(.light)

            UserHeaderView(user: user)
                .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
