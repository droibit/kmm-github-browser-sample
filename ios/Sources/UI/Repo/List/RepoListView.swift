// swiftlint:disable function_default_parameter_at_end

import Shared
import SwiftUI

struct RepoListView: View {
    private let repos: [Repo]

    private let header: String

    private let onLastItemAppear: () -> Void

    init(header: String = "", repos: [Repo], onLastItemAppear: @escaping () -> Void = {}) {
        self.repos = repos
        self.header = header
        self.onLastItemAppear = onLastItemAppear
    }

    var body: some View {
        if repos.isEmpty {
            EmptyView()
        } else {
            VStack(alignment: .leading, spacing: 0) {
                if !header.isEmpty {
                    Text(header)
                        .font(.headline.weight(.regular))
                        .padding(.horizontal)
                }
                List(repos) { repo in
                    NavigationLink(destination: RepoView(repo: repo)) {
                        RepoItemView(repo: repo)
                            .onAppear {
                                if repo === repos.last {
                                    onLastItemAppear()
                                }
                            }
                    }
                }
                .listStyle(PlainListStyle())
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
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
            .background(Color(UIColor.systemBackground))
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .light)

        RepoListView(header: "Repositories", repos: repos) {}
            .background(Color(UIColor.systemBackground))
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .dark)
    }
}
