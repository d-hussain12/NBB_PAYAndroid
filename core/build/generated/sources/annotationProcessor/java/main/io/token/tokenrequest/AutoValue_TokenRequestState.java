

package io.token.tokenrequest;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_TokenRequestState extends TokenRequestState {

  private final String csrfTokenHash;

  private final String innerState;

  AutoValue_TokenRequestState(
      String csrfTokenHash,
      String innerState) {
    if (csrfTokenHash == null) {
      throw new NullPointerException("Null csrfTokenHash");
    }
    this.csrfTokenHash = csrfTokenHash;
    if (innerState == null) {
      throw new NullPointerException("Null innerState");
    }
    this.innerState = innerState;
  }

  @Override
  public String getCsrfTokenHash() {
    return csrfTokenHash;
  }

  @Override
  public String getInnerState() {
    return innerState;
  }

  @Override
  public String toString() {
    return "TokenRequestState{"
         + "csrfTokenHash=" + csrfTokenHash + ", "
         + "innerState=" + innerState
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TokenRequestState) {
      TokenRequestState that = (TokenRequestState) o;
      return (this.csrfTokenHash.equals(that.getCsrfTokenHash()))
           && (this.innerState.equals(that.getInnerState()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= csrfTokenHash.hashCode();
    h$ *= 1000003;
    h$ ^= innerState.hashCode();
    return h$;
  }

}
