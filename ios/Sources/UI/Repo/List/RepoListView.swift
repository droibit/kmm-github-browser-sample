// swiftlint:disable function_default_parameter_at_end

import Shared
import SwiftUI

struct RepoListView: View {
    private let repos: [Repo]

    private let header: String

    private var showsFooterProress: Bool

    private let onLastItemAppear: () -> Void

    init(header: String = "",
         repos: [Repo],
         showsFooterProress: Bool = false,
         onLastItemAppear: @escaping () -> Void = {})
    {
        self.header = header
        self.repos = repos
        self.showsFooterProress = showsFooterProress
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
                List {
                    ForEach(repos) { repo in
                        NavigationLink(destination: RepoView(owner: repo.ownerLogin, name: repo.name)) {
                            RepoItemView(repo: repo)
                                .onAppear {
                                    if repo === repos.last {
                                        onLastItemAppear()
                                    }
                                }
                        }
                    }

                    if showsFooterProress {
                        VStack(alignment: .center) {
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle(tint: .secondary))
                                .padding(8)
                        }.frame(maxWidth: .infinity)
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

        Group {
            RepoListView(repos: repos, showsFooterProress: false)
                .background(Color(UIColor.systemBackground))
                .environment(\.colorScheme, .light)

            RepoListView(header: "Repositories", repos: repos, showsFooterProress: true)
                .background(Color(UIColor.systemBackground))
                .environment(\.colorScheme, .dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
