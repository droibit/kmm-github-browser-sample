import SwiftUI

@main
struct SampleApp: App {
    // swiftlint:disable weak_delegate
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    // swiftlint:enable weak_delegate

    var body: some Scene {
        WindowGroup {
            NavigationView {
                ContentView()
                    .navigationBarTitle(Text("GitHub Browser"), displayMode: .inline)
            }
        }
    }
}
