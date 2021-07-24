import SwiftUI

@main
struct SampleApp: App {
    // swiftlint:disable weak_delegate
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    // swiftlint:enable weak_delegate

    var body: some Scene {
        WindowGroup {
            NavigationView {
                SearchView()
                    .navigationBarTitle(Text("GitHub Browser"), displayMode: .inline)
            }
            // ref. https://stackoverflow.com/a/64752414
            .navigationViewStyle(StackNavigationViewStyle())
        }
    }
}
