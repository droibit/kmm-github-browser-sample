// swiftlint:disable identifier_name

import Combine
import CombineSchedulers
import Foundation
import Shared

class RepoViewModel: ObservableObject {
    private let repoRepository: RepoRepository

    private let mainScheduler: AnySchedulerOf<DispatchQueue>

    private var cancellables = Set<AnyCancellable>()

    @Published private(set) var getRepoUiModel: GetRepoUiModel

    init(repoRepository: RepoRepository,
         mainScheduler: AnySchedulerOf<DispatchQueue>,
         getRepoUiModel: GetRepoUiModel)
    {
        self.repoRepository = repoRepository
        self.mainScheduler = mainScheduler
        self.getRepoUiModel = getRepoUiModel
    }

    deinit {
        Komol.d("deinit: \(type(of: self))")
    }

    func onAppear(owner: String, name: String) {
        guard cancellables.isEmpty else {
            return
        }
        refresh(owner: owner, name: name)
    }

    func refresh(owner: String, name: String) {
        guard !getRepoUiModel.inProgress else {
            return
        }

        Publishers.Zip(
            repoRepository.loadRepo(owner: owner, name: name),
            repoRepository.loadContributors(owner: owner, name: name)
        )
        .receive(on: mainScheduler)
        .sink(with: self) { owner, completion in
            if case let .failure(error) = completion {
                owner.getRepoUiModel = GetRepoUiModel(error: error.message)
            }
        } receiveValue: { owner, value in
            let (_repo, contributors) = value
            guard let repo = _repo else {
                owner.getRepoUiModel = GetRepoUiModel(error: "Unknown repo: \(name)")
                return
            }
            owner.getRepoUiModel = GetRepoUiModel(
                repoUiModel: RepoUiModel(repo: repo, contributors: contributors)
            )
        }.store(in: &cancellables)
    }
}
