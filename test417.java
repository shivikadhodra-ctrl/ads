class Solution {
    int m, n;
    int[][] heights;
    boolean[][] pacific;
    boolean[][] atlantic;

    int[][] directions = {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };

    public List<List<Integer>> pacificAtlantic(int[][] heights) {

        this.heights = heights;
        m = heights.length;
        n = heights[0].length;

        pacific = new boolean[m][n];
        atlantic = new boolean[m][n];

        // Pacific: top row + left column
        for (int c = 0; c < n; c++) {
            dfs(0, c, pacific);
        }

        for (int r = 0; r < m; r++) {
            dfs(r, 0, pacific);
        }

        // Atlantic: bottom row + right column
        for (int c = 0; c < n; c++) {
            dfs(m - 1, c, atlantic);
        }

        for (int r = 0; r < m; r++) {
            dfs(r, n - 1, atlantic);
        }

        // Cells reachable from both oceans
        List<List<Integer>> result = new ArrayList<>();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                if (pacific[r][c] && atlantic[r][c]) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }

        return result;
    }

    private void dfs(int r, int c, boolean[][] ocean) {

        if (ocean[r][c]) {
            return;
        }

        ocean[r][c] = true;

        for (int[] dir : directions) {

            int nr = r + dir[0];
            int nc = c + dir[1];

            // Out of bounds
            if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                continue;
            }

            // Reverse flow:
            // next cell must be >= current cell
            if (heights[nr][nc] < heights[r][c]) {
                continue;
            }

            dfs(nr, nc, ocean);
        }
    }
}