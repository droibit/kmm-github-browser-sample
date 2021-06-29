import Foundation
import Resolver
import Shared

extension Resolver: ResolverRegistering {
    public static func registerAllServices() {
        registerDataSources()
        registerRepositories()
    }

    private static func registerDataSources() {
        register { HttpClientProvider().get() }
            .scope(.application)

        register { GitHubService(httpClient: resolve()) }
            .scope(.application)

        register { AppDatabaseProvider().get() }
            .scope(.application)
    }

    private static func registerRepositories() {
        register {
            UserRepositoryCombine(
                delegate: UserRepositoryIos.Provider().get(
                    gitHubService: resolve(),
                    appDatabase: resolve()
                )
            )
        }.implements(UserRepository.self)
            .scope(.application)

        register {
            RepoRepositoryCombine(
                delegate: RepoRepositoryIos.Provider().get(
                    gitHubService: resolve(),
                    appDatabase: resolve()
                )
            )
        }.implements(RepoRepository.self)
            .scope(.application)
    }
}
