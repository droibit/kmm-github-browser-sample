import Shared
import SwiftUI

struct SearchView: View {
    var body: some View {
        VStack(spacing: 8) {
            SearchHeaderView(disabled: false) { query in
                Komol.d("Submit: \(query)")
            }
            .padding()

            RepoListView(repos: [
                Repo(
                    id: 1,
                    name: "kmm-github-browser-sample",
                    fullName: "droibit/kmm-github-browser-sample",
                    description: "Github Browser sample using Kotlin Multiplatform Mobile.",
                    stars: 0,
                    ownerLogin: "",
                    ownerUrl: ""
                ),
            ]) {}
        }
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemBackground))
            .previewDevice("iPhone SE (2nd generation)")
            .environment(\.colorScheme, .light)

        SearchView()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemBackground))
            .previewDevice("iPhone 12")
            .environment(\.colorScheme, .dark)
    }
}
