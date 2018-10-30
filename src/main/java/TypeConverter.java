public class TypeConverter {
    public double jsonObjectToType(Object obj){
        double nr = 0;
        if (obj instanceof Integer) {
            int i = (Integer) obj;
            nr = i;
        }
        else if (obj instanceof Double) {
            Double d = (Double) obj;
            nr = d;
        }
        else if (obj instanceof String) {
            String s = (String) obj;
            nr = Double.parseDouble(s);
        }
        return nr;
    }
}
