package ru.ifmo.rain.brichev;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        mv("data2", "TlkAgg2", false, true);
        mv("data5", "TlkAgg5", true, true);
        wawa("data2", "TlkAgg2", false, true);
        wawa("data5", "TlkAgg5", true, true);
        dawidSkene2("data2", "TlkAgg2", false, true);
        dawidSkene5("data5", "TlkAgg5", true, true);
    }

    public static void dawidSkene5(String sourceDir, String dirName, boolean isMultiple, boolean createTsv) throws FileNotFoundException {
        File data = new File(sourceDir + "/data.tsv");
        TSVReader mvReader = new TSVReader(data);
        Map<String, List<Integer>> dataset = new HashMap<>();
        Map<String, List<Pair<String, Integer>>> workers = new HashMap<>();
        Set<String> tasks = new TreeSet<>();

        while (mvReader.hasNextTokens()) {
            String[] row = mvReader.nextTokens();
            tasks.add(row[1]);
            if (!dataset.containsKey(row[1])) {
                dataset.put(row[1], new ArrayList<>());
            }
            if (row.length == 3) {
                dataset.get(row[1]).add(Integer.valueOf(row[2]));
            } else {
                dataset.get(row[1]).add(null);
            }

            if (!workers.containsKey(row[0])) {
                workers.put(row[0], new ArrayList<>());
            }

            if (row.length == 3) {
                workers.get(row[0]).add(new Pair<>(row[1], Integer.valueOf(row[2])));
            } else {
                workers.get(row[0]).add(new Pair<>(row[1], null));
            }

        }
        Map<String, Map<Integer, Double>> matrix = new TreeMap<>();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                matrix = getMvMatrix5(sourceDir);
            }

            Map<String, Map<String, Double>> workersProbs = new TreeMap<>();

            for (var e : workers.entrySet()) {
                Map<String, Double> probsMatrix = new LinkedHashMap<>();
                for (int t = 1; t <= 5; t++) {
                    for (int t2 = 1; t2 <= 5; t2++) {
                        probsMatrix.put(t + String.valueOf(t2), 0d);
                    }
                }
                for (var pair : e.getValue()) {
                    if (pair.value != null) {
                        if (pair.value == 1) {
                            for (int t = 1; t <= 5; t++) {
                                probsMatrix.put(String.valueOf(t) + 1, probsMatrix.get(String.valueOf(t) + 1) + BigDecimal.valueOf(matrix.get(pair.key).get(t)).setScale(3, RoundingMode.HALF_UP).doubleValue());
                            }
                        } else if (pair.value == 2) {
                            for (int t = 1; t <= 5; t++) {
                                probsMatrix.put(String.valueOf(t) + 2, probsMatrix.get(String.valueOf(t) + 2) + BigDecimal.valueOf(matrix.get(pair.key).get(t)).setScale(3, RoundingMode.HALF_UP).doubleValue());
                            }
                        } else if (pair.value == 3) {
                            for (int t = 1; t <= 5; t++) {
                                probsMatrix.put(String.valueOf(t) + 3, probsMatrix.get(String.valueOf(t) + 3) + BigDecimal.valueOf(matrix.get(pair.key).get(t)).setScale(3, RoundingMode.HALF_UP).doubleValue());
                            }
                        } else if (pair.value == 4) {
                            for (int t = 1; t <= 5; t++) {
                                probsMatrix.put(String.valueOf(t) + 4, probsMatrix.get(String.valueOf(t) + 4) + BigDecimal.valueOf(matrix.get(pair.key).get(t)).setScale(3, RoundingMode.HALF_UP).doubleValue());
                            }
                        } else if (pair.value == 5) {
                            for (int t = 1; t <= 5; t++) {
                                probsMatrix.put(String.valueOf(t) + 5, probsMatrix.get(String.valueOf(t) + 5) + BigDecimal.valueOf(matrix.get(pair.key).get(t)).setScale(3, RoundingMode.HALF_UP).doubleValue());
                            }
                        }
                    }
                }


                Map<String, Double> normProbsMatrix = new LinkedHashMap<>();
                for (int t = 1; t <= 5; t++) {
                    for (int t2 = 1; t2 <= 5; t2++) {
                        normProbsMatrix.put(t + String.valueOf(t2), 0d);
                    }
                }

                for (int ii = 1; ii <= 5; ii++) {
                    double rowSum = 0;
                    for (int jj = 1; jj <= 5; jj++) {
                        rowSum += probsMatrix.get(ii + String.valueOf(jj));
                    }
                    for (int jj = 1; jj <= 5; jj++) {
                        if (rowSum != 0) {
                            normProbsMatrix.put(String.valueOf(ii) + jj, probsMatrix.get(String.valueOf(ii) + jj) / rowSum);
                        } else {
                            normProbsMatrix.put(String.valueOf(ii) + jj, 0d);
                        }
                    }
                }

                workersProbs.put(e.getKey(), normProbsMatrix);
            }


            BigDecimal probsO = BigDecimal.ZERO;
            BigDecimal probsTw = BigDecimal.ZERO;
            BigDecimal probsTh = BigDecimal.ZERO;
            BigDecimal probsF = BigDecimal.ZERO;
            BigDecimal probsFv = BigDecimal.ZERO;
            for (var e : matrix.entrySet()) {
                probsO = probsO.add(BigDecimal.valueOf(e.getValue().get(1)));
                probsTw = probsTw.add(BigDecimal.valueOf(e.getValue().get(2)));
                probsTh = probsTh.add(BigDecimal.valueOf(e.getValue().get(3)));
                probsF = probsF.add(BigDecimal.valueOf(e.getValue().get(4)));
                probsFv = probsFv.add(BigDecimal.valueOf(e.getValue().get(5)));
            }


            BigDecimal sum = probsO.add(probsTw).add(probsTh).add(probsF).add(probsFv);

            double normProbsO = probsO.divide(sum, RoundingMode.HALF_UP).doubleValue();
            double normProbsTw = probsTw.divide(sum, RoundingMode.HALF_UP).doubleValue();
            double normProbsTh = probsTh.divide(sum, RoundingMode.HALF_UP).doubleValue();
            double normProbsF = probsF.divide(sum, RoundingMode.HALF_UP).doubleValue();
            double normProbsFv = probsFv.divide(sum, RoundingMode.HALF_UP).doubleValue();


            matrix = new TreeMap<>();
            for (var t : tasks) {
                Map<Integer, Double> wrap = new HashMap<>();
                wrap.put(1, normProbsO);
                wrap.put(2, normProbsTw);
                wrap.put(3, normProbsTh);
                wrap.put(4, normProbsF);
                wrap.put(5, normProbsFv);
                matrix.put(t, wrap);
            }
            for (var e : workers.entrySet()) {
                for (var pair : e.getValue()) {
                    double newO = 0;
                    double newTw = 0;
                    double newTh = 0;
                    double newF = 0;
                    double newFv = 0;
                    if (pair.value != null) {
                        if (pair.value == 1) {
                            newO = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("11");
                            newTw = matrix.get(pair.key).get(2) * workersProbs.get(e.getKey()).get("21");
                            newTh = matrix.get(pair.key).get(3) * workersProbs.get(e.getKey()).get("31");
                            newF = matrix.get(pair.key).get(4) * workersProbs.get(e.getKey()).get("41");
                            newFv = matrix.get(pair.key).get(5) * workersProbs.get(e.getKey()).get("51");
                        } else if (pair.value == 2) {
                            newO = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("12");
                            newTw = matrix.get(pair.key).get(2) * workersProbs.get(e.getKey()).get("22");
                            newTh = matrix.get(pair.key).get(3) * workersProbs.get(e.getKey()).get("32");
                            newF = matrix.get(pair.key).get(4) * workersProbs.get(e.getKey()).get("42");
                            newFv = matrix.get(pair.key).get(5) * workersProbs.get(e.getKey()).get("52");
                        } else if (pair.value == 3) {
                            newO = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("13");
                            newTw = matrix.get(pair.key).get(2) * workersProbs.get(e.getKey()).get("23");
                            newTh = matrix.get(pair.key).get(3) * workersProbs.get(e.getKey()).get("33");
                            newF = matrix.get(pair.key).get(4) * workersProbs.get(e.getKey()).get("43");
                            newFv = matrix.get(pair.key).get(5) * workersProbs.get(e.getKey()).get("53");
                        } else if (pair.value == 4) {
                            newO = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("14");
                            newTw = matrix.get(pair.key).get(2) * workersProbs.get(e.getKey()).get("24");
                            newTh = matrix.get(pair.key).get(3) * workersProbs.get(e.getKey()).get("34");
                            newF = matrix.get(pair.key).get(4) * workersProbs.get(e.getKey()).get("44");
                            newFv = matrix.get(pair.key).get(5) * workersProbs.get(e.getKey()).get("54");
                        } else if (pair.value == 5) {
                            newO = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("15");
                            newTw = matrix.get(pair.key).get(2) * workersProbs.get(e.getKey()).get("25");
                            newTh = matrix.get(pair.key).get(3) * workersProbs.get(e.getKey()).get("35");
                            newF = matrix.get(pair.key).get(4) * workersProbs.get(e.getKey()).get("45");
                            newFv = matrix.get(pair.key).get(5) * workersProbs.get(e.getKey()).get("55");
                        }

                        double eps = 0.0000001;
                        matrix.get(pair.key).clear();
                        if (newO < eps) {
                            newO = eps;
                        }
                        if (newTw < eps) {
                            newTw = eps;
                        }
                        if (newTh < eps) {
                            newTh = eps;
                        }
                        if (newF < eps) {
                            newF = eps;
                        }
                        if (newFv < eps) {
                            newFv = eps;
                        }
                        matrix.get(pair.key).put(1, newO);
                        matrix.get(pair.key).put(2, newTw);
                        matrix.get(pair.key).put(3, newTh);
                        matrix.get(pair.key).put(4, newF);
                        matrix.get(pair.key).put(5, newFv);
                    }
                }
            }

            for (var e : matrix.entrySet()) {
                double sum_ = 0;
                for (var t : e.getValue().entrySet()) {
                    sum_ += t.getValue();
                }
                normProbsO = e.getValue().get(1) / sum_;
                normProbsTw = e.getValue().get(2) / sum_;
                normProbsTh = e.getValue().get(3) / sum_;
                normProbsF = e.getValue().get(4) / sum_;
                normProbsFv = e.getValue().get(5) / sum_;

                e.getValue().clear();
                e.getValue().put(1, BigDecimal.valueOf(normProbsO).setScale(5, RoundingMode.HALF_UP).doubleValue());
                e.getValue().put(2, BigDecimal.valueOf(normProbsTw).setScale(5, RoundingMode.HALF_UP).doubleValue());
                e.getValue().put(3, BigDecimal.valueOf(normProbsTh).setScale(5, RoundingMode.HALF_UP).doubleValue());
                e.getValue().put(4, BigDecimal.valueOf(normProbsF).setScale(5, RoundingMode.HALF_UP).doubleValue());
                e.getValue().put(5, BigDecimal.valueOf(normProbsFv).setScale(5, RoundingMode.HALF_UP).doubleValue());
            }
        }

        Map<String, Integer> result = new TreeMap<>();
        for (var e : matrix.entrySet()) {
            double maxVal = -1;
            Integer maxLabel = -1;
            for (var k : e.getValue().entrySet()) {
                if (k.getValue() > maxVal) {
                    maxVal = k.getValue();
                    maxLabel = k.getKey();
                }
            }
            result.put(e.getKey(), maxLabel);
        }

        getAccuracy("ds", result, sourceDir, isMultiple);
        if (createTsv) {
            toTsv(dirName, "ds", result);
        }
    }

    private static Map<String, Map<Integer, Double>> getMvMatrix5(String sourceDir) throws FileNotFoundException {
        File data = new File(sourceDir + "/data.tsv");
        TSVReader mvReader = new TSVReader(data);
        Map<String, List<Integer>> mvMap = new HashMap<>();

        while (mvReader.hasNextTokens()) {
            String[] row = mvReader.nextTokens();
            if (!mvMap.containsKey(row[1])) {
                mvMap.put(row[1], new ArrayList<>());
            }
            if (row.length == 3) {
                mvMap.get(row[1]).add(Integer.valueOf(row[2]));
            } else {
                mvMap.get(row[1]).add(null);
            }
        }
        Map<String, Map<Integer, Double>> matrix = new TreeMap<>();
        for (var e : mvMap.entrySet()) {
            Map<Integer, Integer> integersCount = new HashMap<>();
            for (Integer i : e.getValue()) {
                if (i != null) {
                    if (!integersCount.containsKey(i))
                        integersCount.put(i, 1);
                    else
                        integersCount.put(i, integersCount.get(i) + 1);
                }
            }

            double sum = 0;
            for (var i : integersCount.entrySet()) {
                sum += i.getValue();
            }

            Map<Integer, Double> wrap = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                if (integersCount.containsKey(i)) {
                    double probLabel = (double) integersCount.get(i) / sum;
                    wrap.put(i, probLabel);
                } else {
                    wrap.put(i, 0d);
                }
            }
            matrix.put(e.getKey(), wrap);
        }
        return matrix;
    }


    public static void dawidSkene2(String sourceDir, String dirName, boolean isMultiple, boolean createTsv) throws FileNotFoundException {
        File data = new File(sourceDir + "/data.tsv");
        TSVReader mvReader = new TSVReader(data);
        Map<String, List<Integer>> dataset = new HashMap<>();
        Map<String, List<Pair<String, Integer>>> workers = new HashMap<>();
        Set<String> tasks = new TreeSet<>();

        while (mvReader.hasNextTokens()) {
            String[] row = mvReader.nextTokens();
            tasks.add(row[1]);
            if (!dataset.containsKey(row[1])) {
                dataset.put(row[1], new ArrayList<>());
            }
            if (row.length == 3) {
                dataset.get(row[1]).add(Integer.valueOf(row[2]));
            } else {
                dataset.get(row[1]).add(null);
            }

            if (!workers.containsKey(row[0])) {
                workers.put(row[0], new ArrayList<>());
            }

            if (row.length == 3) {
                workers.get(row[0]).add(new Pair<>(row[1], Integer.valueOf(row[2])));
            } else {
                workers.get(row[0]).add(new Pair<>(row[1], null));
            }

        }
        Map<String, Map<Integer, Double>> matrix = new TreeMap<>();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                matrix = getMvMatrix2(sourceDir);
            }

            Map<String, Map<String, Double>> workersProbs = new TreeMap<>();
            for (var e : workers.entrySet()) {
                double noyes = 0;
                double nono = 0;
                double yesno = 0;
                double yesyes = 0;
                for (var pair : e.getValue()) {
                    if (pair.value != null) {
                        if (pair.value == 0) {
                            nono += BigDecimal.valueOf(matrix.get(pair.key).get(0)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                            yesno += BigDecimal.valueOf(matrix.get(pair.key).get(1)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                        } else {
                            noyes += BigDecimal.valueOf(matrix.get(pair.key).get(0)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                            yesyes += BigDecimal.valueOf(matrix.get(pair.key).get(1)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                        }
                    }
                }
                double normnono = 0;
                double normnoyes = 0;
                double normyesyes = 0;
                double normyesno = 0;
                if (nono + noyes != 0) {
                    normnono = BigDecimal.valueOf(nono / (nono + noyes)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    normnoyes = BigDecimal.valueOf(noyes / (nono + noyes)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                }
                if (yesno + yesyes != 0) {
                    normyesno = BigDecimal.valueOf(yesno / (yesno + yesyes)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    normyesyes = BigDecimal.valueOf(yesyes / (yesno + yesyes)).setScale(3, RoundingMode.HALF_UP).doubleValue();
                }


                Map<String, Double> wrap = new TreeMap<>();
                wrap.put("nono", normnono);
                wrap.put("noyes", normnoyes);
                wrap.put("yesyes", normyesyes);
                wrap.put("yesno", normyesno);
                workersProbs.put(e.getKey(), wrap);
            }

            BigDecimal probsNo = BigDecimal.ZERO;
            BigDecimal probsYes = BigDecimal.ZERO;
            for (var e : matrix.entrySet()) {
                probsNo = probsNo.add(BigDecimal.valueOf(e.getValue().get(0)));
                probsYes = probsYes.add(BigDecimal.valueOf(e.getValue().get(1)));
            }

            double normProbsNo = probsNo.divide(probsNo.add(probsYes), RoundingMode.HALF_UP).doubleValue();
            double normProbsYes = probsYes.divide(probsYes.add(probsYes), RoundingMode.HALF_UP).doubleValue();

            matrix = new TreeMap<>();
            for (var t : tasks) {
                Map<Integer, Double> wrap = new HashMap<>();
                wrap.put(0, normProbsNo);
                wrap.put(1, normProbsYes);
                matrix.put(t, wrap);
            }

            for (var e : workers.entrySet()) {
                for (var pair : e.getValue()) {
                    double newno;
                    double newyes;
                    if (pair.value != null) {
                        if (pair.value == 0) {
                            newno = matrix.get(pair.key).get(0) * workersProbs.get(e.getKey()).get("nono");
                            newyes = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("yesno");
                        } else {
                            newno = matrix.get(pair.key).get(0) * workersProbs.get(e.getKey()).get("noyes");
                            newyes = matrix.get(pair.key).get(1) * workersProbs.get(e.getKey()).get("yesyes");
                        }
                        double eps = 0.0000001;
                        matrix.get(pair.key).clear();
                        if (newno < eps) {
                            newno = eps;
                        }
                        if (newyes < eps) {
                            newyes = eps;
                        }
                        matrix.get(pair.key).put(0, newno);
                        matrix.get(pair.key).put(1, newyes);
                    }
                }
            }

            for (var e : matrix.entrySet()) {
                normProbsNo = e.getValue().get(0) / (e.getValue().get(0) + e.getValue().get(1));
                normProbsYes = e.getValue().get(1) / (e.getValue().get(0) + e.getValue().get(1));
                e.getValue().clear();
                e.getValue().put(0, BigDecimal.valueOf(normProbsNo).setScale(5, RoundingMode.HALF_UP).doubleValue());
                e.getValue().put(1, BigDecimal.valueOf(normProbsYes).setScale(5, RoundingMode.HALF_UP).doubleValue());
            }
        }

        Map<String, Integer> result = new TreeMap<>();
        for (var e : matrix.entrySet()) {
            if (e.getValue().get(0) > e.getValue().get(1)) {
                result.put(e.getKey(), 0);
            } else {
                result.put(e.getKey(), 1);
            }
        }
        getAccuracy("ds", result, sourceDir, isMultiple);
        if (createTsv) {
            toTsv(dirName, "ds", result);
        }
    }

    private static Map<String, Map<Integer, Double>> getMvMatrix2(String sourceDir) throws FileNotFoundException {
        File data = new File(sourceDir + "/data.tsv");
        TSVReader mvReader = new TSVReader(data);
        Map<String, List<Integer>> mvMap = new HashMap<>();

        while (mvReader.hasNextTokens()) {
            String[] row = mvReader.nextTokens();
            if (!mvMap.containsKey(row[1])) {
                mvMap.put(row[1], new ArrayList<>());
            }
            if (row.length == 3) {
                mvMap.get(row[1]).add(Integer.valueOf(row[2]));
            } else {
                mvMap.get(row[1]).add(null);
            }
        }
        Map<String, Map<Integer, Double>> matrix = new TreeMap<>();
        for (var e : mvMap.entrySet()) {
            Map<Integer, Integer> integersCount = new HashMap<>();
            for (Integer i : e.getValue()) {
                if (i != null) {
                    if (!integersCount.containsKey(i))
                        integersCount.put(i, 1);
                    else
                        integersCount.put(i, integersCount.get(i) + 1);
                }
            }
            double yes;
            double no;
            if (!integersCount.containsKey(0)) {
                yes = 1;
                no = 0;
            } else if (!integersCount.containsKey(1)) {
                yes = 0;
                no = 1;
            } else {
                yes = (double) integersCount.get(1) / (integersCount.get(0) + integersCount.get(1));
                no = (double) integersCount.get(0) / (integersCount.get(0) + integersCount.get(1));
            }

            Map<Integer, Double> wrap = new HashMap<>();
            wrap.put(0, no);
            wrap.put(1, yes);
            matrix.put(e.getKey(), wrap);
        }
        return matrix;
    }

    public static Map<String, Integer> mv(String sourceDir, String dirName, boolean isMultiple, boolean createTsv) throws FileNotFoundException {
        File data = new File(sourceDir + "/data.tsv");
        TSVReader mvReader = new TSVReader(data);
        Map<String, List<Integer>> mvMap = new HashMap<>();

        while (mvReader.hasNextTokens()) {
            String[] row = mvReader.nextTokens();
            if (!mvMap.containsKey(row[1])) {
                mvMap.put(row[1], new ArrayList<>());
            }
            if (row.length == 3) {
                mvMap.get(row[1]).add(Integer.valueOf(row[2]));
            } else {
                mvMap.get(row[1]).add(null);
            }
        }

        Map<String, Integer> result = new TreeMap<>();
        for (var e : mvMap.entrySet()) {
            Map<Integer, Integer> integersCount = new HashMap<>();
            for (Integer i : e.getValue()) {
                if (i != null) {
                    if (!integersCount.containsKey(i))
                        integersCount.put(i, 1);
                    else
                        integersCount.put(i, integersCount.get(i) + 1);
                }
            }

            int maxLabel = -1;
            int maxValue = -1;
            List<Integer> similar = new ArrayList<>();
            for (var t : integersCount.entrySet()) {
                if (t.getValue() > maxValue) {
                    maxLabel = t.getKey();
                    maxValue = t.getValue();
                } else if (t.getValue() == maxValue) {
                    if (!similar.contains(t.getKey())) {
                        similar.add(t.getKey());
                    }
                    if (!similar.contains(maxLabel)) {
                        similar.add(maxLabel);
                    }
                }
            }

            if (similar.size() > 0) {
                int min = 0;
                int max = similar.size() - 1;
                int diff = max - min;
                Random random = new Random();
                int i = random.nextInt(diff + 1);
                i += min;
                maxLabel = similar.get(i);
            }
            result.put(e.getKey(), maxLabel);
        }
        getAccuracy("mv", result, sourceDir, isMultiple);
        if (createTsv) {
            toTsv(dirName, "mv", result);
        }
        return result;
    }

    public static void wawa(String sourceDir, String dirName, boolean isMultiple, boolean createTsv) throws FileNotFoundException {
        Map<String, Integer> mv = mv(sourceDir, dirName, isMultiple, createTsv);
        File data = new File(sourceDir + "/data.tsv");
        TSVReader mvReader = new TSVReader(data);

        Map<String, List<Pair<String, Integer>>> workers = new HashMap<>();
        Map<String, List<Pair<String, Integer>>> tasks = new HashMap<>();

        while (mvReader.hasNextTokens()) {
            String[] row = mvReader.nextTokens();
            if (!workers.containsKey(row[0])) {
                workers.put(row[0], new ArrayList<>());
            }
            workers.get(row[0]).add(new Pair<>(row[1], Integer.parseInt(row[2])));
            if (!tasks.containsKey(row[1])) {
                tasks.put(row[1], new ArrayList<>());
            }
            tasks.get(row[1]).add(new Pair<>(row[0], Integer.parseInt(row[2])));
        }

        Map<String, Double> workerAccuracy = new HashMap<>();
        for (var e : workers.entrySet()) {
            double correct = 0;
            for (var k : e.getValue()) {
                if (mv.get(k.key).equals(k.value)) {
                    correct += 1;
                }
            }
            workerAccuracy.put(e.getKey(), correct / e.getValue().size());
        }

        Map<String, Integer> result = new TreeMap<>();
        for (var e : tasks.entrySet()) {
            List<Integer> similar = new ArrayList<>();
            Map<Integer, Double> count = new HashMap<>();
            for (var k : e.getValue()) {
                double acc = workerAccuracy.get(k.key);
                if (!count.containsKey(k.value)) {
                    count.put(k.value, acc);
                } else {
                    count.put(k.value, count.get(k.value) + acc);
                }
            }

            Integer maxLabel = -1;
            double maxValue = -1d;
            for (var t : count.entrySet()) {
                if (t.getValue() > maxValue) {
                    maxLabel = t.getKey();
                    maxValue = t.getValue();
                } else if (t.getValue() == maxValue) {
                    if (!similar.contains(t.getKey())) {
                        similar.add(t.getKey());
                    }
                    if (!similar.contains(maxLabel)) {
                        similar.add(maxLabel);
                    }
                }
            }

            if (similar.size() > 0) {
                int min = 0;
                int max = similar.size() - 1;
                int diff = max - min;
                Random random = new Random();
                int i = random.nextInt(diff + 1);
                i += min;
                maxLabel = similar.get(i);
            }
            result.put(e.getKey(), maxLabel);
        }
        getAccuracy("wawa", result, sourceDir, isMultiple);
        if (createTsv) {
            toTsv(dirName, "wawa", result);
        }
    }

    public static class Pair<K, V> {
        public K key;
        public V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key +
                    ", " + value + "}";
        }
    }


    public static void toTsv(String dirName, String fileName, Map<String, Integer> result) {
        try {
            FileWriter fos = new FileWriter(dirName + "/" + fileName + ".tsv");
            PrintWriter dos = new PrintWriter(fos);
            for (var e : result.entrySet()) {
                dos.print(e.getKey() + "\t");
                dos.print(e.getValue() + "\t");
                dos.println();
            }
            dos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Printing Tab Delimited File");
        }
    }


    public static void getAccuracy(String name, Map<String, Integer> data, String sourceDir, boolean isMultiple) throws FileNotFoundException {
        File mv = new File(sourceDir + "/golden_labels.tsv");
        TSVReader mvReader = new TSVReader(mv);

        if (!isMultiple) {
            Map<String, Integer> goldenMap = new HashMap<>();
            while (mvReader.hasNextTokens()) {
                String[] row = mvReader.nextTokens();
                goldenMap.put(row[0], Integer.valueOf(row[1]));
            }
            double rightAsw = 0;

            for (var e : goldenMap.entrySet()) {
                if (e.getValue().equals(data.get(e.getKey()))) {
                    rightAsw += 1;
                }
            }
            System.out.println(name + " " + rightAsw / goldenMap.size());
        } else {
            Map<String, List<Integer>> goldenMap = new HashMap<>();
            while (mvReader.hasNextTokens()) {
                String[] row = mvReader.nextTokens();
                if (!goldenMap.containsKey(row[0])) {
                    goldenMap.put(row[0], new ArrayList<>());
                }
                goldenMap.get(row[0]).add(Integer.valueOf(row[1]));
            }
            double rightAsw = 0;
            for (var e : goldenMap.entrySet()) {
                if (e.getValue().contains(data.get(e.getKey()))) {
                    rightAsw += 1;
                }
            }
            System.out.println(name + " " + rightAsw / goldenMap.size());
        }
    }


    public static class TSVReader implements Closeable {
        final Scanner in;
        String peekLine = null;

        public TSVReader(File f) throws FileNotFoundException {
            in = new Scanner(f);
        }

        public boolean hasNextTokens() {
            if (peekLine != null) return true;
            if (!in.hasNextLine()) {
                return false;
            }
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                return hasNextTokens();
            }
            this.peekLine = line;
            return true;
        }

        public String[] nextTokens() {
            if (!hasNextTokens()) return null;
            String[] tokens = peekLine.split("[\\s\t]+");
            peekLine = null;
            return tokens;
        }

        @Override
        public void close() throws IOException {
            in.close();
        }
    }
}
