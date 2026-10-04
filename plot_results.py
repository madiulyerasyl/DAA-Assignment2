import csv
import os
import matplotlib.pyplot as plt

CSV_FILE = "results/results.csv"
PLOTS_DIR = "results/plots"

os.makedirs(PLOTS_DIR, exist_ok=True)

rows = []

with open(CSV_FILE, newline="") as file:
    reader = csv.DictReader(file)

    for row in reader:
        row["n"] = int(row["n"])
        row["time_ms"] = float(row["time_ms"])
        row["steps"] = int(row["steps"])
        row["moves"] = int(row["moves"])
        row["comparisons"] = int(row["comparisons"])
        rows.append(row)


def make_plot(workload, variant, metric, filename):
    selected = []

    for row in rows:
        if row["workload"] == workload and row["variant"] == variant:
            selected.append(row)

    structures = []

    for row in selected:
        if row["structure"] not in structures:
            structures.append(row["structure"])

    for structure in structures:
        structure_rows = []

        for row in selected:
            if row["structure"] == structure:
                structure_rows.append(row)

        structure_rows.sort(key=lambda row: row["n"])

        x = [row["n"] for row in structure_rows]
        y = [row[metric] for row in structure_rows]

        plt.plot(x, y, marker="o", label=structure)

    plt.xlabel("n")

    if metric == "time_ms":
        plt.ylabel("Time (ms)")
    else:
        plt.ylabel(metric.capitalize())

    title = workload

    if variant != "-":
        title += " - " + variant

    title += " - " + metric

    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.tight_layout()

    plt.savefig(PLOTS_DIR + "/" + filename)
    plt.close()

# W1 - Random Access
make_plot("W1", "-", "time_ms", "W1_time.png")
make_plot("W1", "-", "steps", "W1_steps.png")

# W2 - Search
make_plot("W2", "-", "time_ms", "W2_time.png")
make_plot("W2", "-", "comparisons", "W2_comparisons.png")

# W3 - Insert & Remove at head
make_plot("W3", "head", "time_ms", "W3_head_time.png")
make_plot("W3", "head", "moves", "W3_head_moves.png")

# W3 - Insert & Remove at middle
make_plot("W3", "middle", "time_ms", "W3_middle_time.png")
make_plot("W3", "middle", "moves", "W3_middle_moves.png")

# W4 - Priority Processing
make_plot("W4", "-", "time_ms", "W4_time.png")
make_plot("W4", "-", "steps", "W4_steps.png")
make_plot("W4", "-", "moves", "W4_moves.png")
make_plot("W4", "-", "comparisons", "W4_comparisons.png")

print("Plots created successfully.")