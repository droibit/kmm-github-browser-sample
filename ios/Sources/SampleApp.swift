import SwiftUI

@main
struct SampleApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

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
