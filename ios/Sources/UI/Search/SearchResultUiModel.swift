import Foundation
import Shared

struct SearchResultUiModel {
    let inProgress: Bool
    let searchResult: PagedRepoSearchResult?
    let error: String?

    var hasNoState: Bool {
        !inProgress && searchResult == nil
    }

    var firstInProgress: Bool {
        inProgress && searchResult == nil
    }

    var pagingInProgress: Bool {
        inProgress && searchResult != nil
    }

    init(inProgress: Bool = false,
         searchResult: PagedRepoSearchResult? = nil,
         error: String? = nil)
    {
        self.inProgress = inProgress
        self.searchResult = searchResult
        self.error = error
    }
}

extension PagedRepoSearchResult {
    func merge(existingRepos repos: [Repo]) -> PagedRepoSearchResult {
        PagedRepoSearchResult(repos: repos + self.repos, nextPage: nextPage)
    }
}