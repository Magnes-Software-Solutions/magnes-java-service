package software.magnes.solutions.alerts;

import java.util.ArrayList;
import java.util.List;

public class AlertService {

    private static List<Alertas> alerts = new ArrayList<>();

    public static void addAlertas(Alertas alert) {
        alerts.add(alert);
    }

    public static List<Alertas> getAlertass() {
        return alerts;
    }
}
