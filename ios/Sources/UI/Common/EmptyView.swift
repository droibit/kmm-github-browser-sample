import SwiftUI

struct EmptyView: View {
    var body: some View {
        VStack(alignment: .center) {
            Text("No data")
        }
        .font(.body)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct EmptyView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            EmptyView()
                .preferredColorScheme(.light)

            EmptyView()
                .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
