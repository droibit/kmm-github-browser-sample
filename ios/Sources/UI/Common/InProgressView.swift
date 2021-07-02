import SwiftUI

struct InProgressView: View {
    var body: some View {
        VStack(alignment: .center) {
            ProgressView("Loading...")
                .progressViewStyle(CircularProgressViewStyle(tint: .secondary))
                .foregroundColor(.primary)
                .font(.body)
                .padding()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct InProgressView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            InProgressView()
                .background(Color(UIColor.systemBackground))
                .preferredColorScheme(.light)

            InProgressView()
                .background(Color(UIColor.systemBackground))
                .preferredColorScheme(.dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
